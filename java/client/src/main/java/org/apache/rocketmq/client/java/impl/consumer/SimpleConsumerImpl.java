/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.client.java.impl.consumer;

import apache.rocketmq.v2.AckMessageResponse;
import apache.rocketmq.v2.ChangeInvisibleDurationResponse;
import apache.rocketmq.v2.Code;
import apache.rocketmq.v2.ReceiveMessageRequest;
import apache.rocketmq.v2.Status;
import com.google.common.math.IntMath;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.javacrumbs.futureconverter.java8guava.FutureConverter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.apache.rocketmq.client.java.exception.BadRequestException;
import org.apache.rocketmq.client.java.exception.ForbiddenException;
import org.apache.rocketmq.client.java.exception.InternalErrorException;
import org.apache.rocketmq.client.java.exception.NotFoundException;
import org.apache.rocketmq.client.java.exception.ProxyTimeoutException;
import org.apache.rocketmq.client.java.exception.TooManyRequestsException;
import org.apache.rocketmq.client.java.exception.UnauthorizedException;
import org.apache.rocketmq.client.java.exception.UnsupportedException;
import org.apache.rocketmq.client.java.impl.ClientSettings;
import org.apache.rocketmq.client.java.message.MessageViewImpl;
import org.apache.rocketmq.client.java.message.protocol.Resource;
import org.apache.rocketmq.client.java.route.MessageQueueImpl;
import org.apache.rocketmq.client.java.route.TopicRouteDataResult;
import org.apache.rocketmq.client.java.rpc.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link SimpleConsumer}
 */
@SuppressWarnings("UnstableApiUsage")
class SimpleConsumerImpl extends ConsumerImpl implements SimpleConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConsumerImpl.class);

    private final SimpleConsumerSettings simpleConsumerSettings;
    private final String consumerGroup;
    private final Duration awaitDuration;

    private final AtomicInteger topicIndex;

    private final Map<String /* topic */, FilterExpression> subscriptionExpressions;
    private final ConcurrentMap<String /* topic */, SubscriptionLoadBalancer> subTopicRouteDataResultCache;

    public SimpleConsumerImpl(ClientConfiguration clientConfiguration, String consumerGroup, Duration awaitDuration,
        Map<String, FilterExpression> subscriptionExpressions) {
        super(clientConfiguration, consumerGroup, subscriptionExpressions.keySet());
        Resource groupResource = new Resource(consumerGroup);
        this.simpleConsumerSettings = new SimpleConsumerSettings(clientId, accessEndpoints, groupResource,
            clientConfiguration.getRequestTimeout(), awaitDuration, subscriptionExpressions);
        this.consumerGroup = consumerGroup;
        this.awaitDuration = awaitDuration;

        this.topicIndex = new AtomicInteger(RandomUtils.nextInt(0, Integer.MAX_VALUE));

        this.subscriptionExpressions = subscriptionExpressions;
        this.subTopicRouteDataResultCache = new ConcurrentHashMap<>();
    }

    @Override
    protected void startUp() throws Exception {
        try {
            LOGGER.info("Begin to start the rocketmq simple consumer, clientId={}", clientId);
            super.startUp();
            LOGGER.info("The rocketmq simple consumer starts successfully, clientId={}", clientId);
        } catch (Throwable t) {
            LOGGER.error("Failed to start the rocketmq simple consumer, try to shutdown it, clientId={}", clientId, t);
            shutDown();
            throw t;
        }
    }

    @Override
    protected void shutDown() throws InterruptedException {
        LOGGER.info("Begin to shutdown the rocketmq simple consumer, clientId={}", clientId);
        super.shutDown();
        LOGGER.info("Shutdown the rocketmq simple consumer successfully, clientId={}", clientId);
    }

    /**
     * @see SimpleConsumer#getConsumerGroup()
     */
    @Override
    public String getConsumerGroup() {
        return consumerGroup;
    }

    /**
     * @see SimpleConsumer#subscribe(String, FilterExpression)
     */
    @Override
    public SimpleConsumer subscribe(String topic, FilterExpression filterExpression) throws ClientException {
        // Check consumer status.
        if (!this.isRunning()) {
            LOGGER.error("Unable to add subscription because simple consumer is not running, state={}, clientId={}",
                this.state(), clientId);
            throw new IllegalStateException("Simple consumer is not running now");
        }
        final ListenableFuture<TopicRouteDataResult> future = getRouteDataResult(topic);
        TopicRouteDataResult topicRouteDataResult = handleClientFuture(future);
        topicRouteDataResult.checkAndGetTopicRouteData();
        subscriptionExpressions.put(topic, filterExpression);
        return this;
    }

    /**
     * @see SimpleConsumer#unsubscribe(String)
     */
    @Override
    public SimpleConsumer unsubscribe(String topic) {
        if (!this.isRunning()) {
            LOGGER.error("Unable to remove subscription because simple consumer is not running, state={}, "
                + "clientId={}", this.state(), clientId);
            throw new IllegalStateException("Simple consumer is not running now");
        }
        subscriptionExpressions.remove(topic);
        return this;
    }

    /**
     * @see SimpleConsumer#getSubscriptionExpressions()
     */
    @Override
    public Map<String, FilterExpression> getSubscriptionExpressions() {
        return new HashMap<>(subscriptionExpressions);
    }

    /**
     * @see SimpleConsumer#receive(int, Duration)
     */
    @Override
    public List<MessageView> receive(int maxMessageNum, Duration invisibleDuration) throws ClientException {
        final ListenableFuture<List<MessageView>> future = receive0(maxMessageNum, invisibleDuration);
        return handleClientFuture(future);
    }

    /**
     * @see SimpleConsumer#receiveAsync(int, Duration)
     */
    @Override
    public CompletableFuture<List<MessageView>> receiveAsync(int maxMessageNum, Duration invisibleDuration) {
        final ListenableFuture<List<MessageView>> future = receive0(maxMessageNum, invisibleDuration);
        return FutureConverter.toCompletableFuture(future);
    }

    public ListenableFuture<List<MessageView>> receive0(int maxMessageNum, Duration invisibleDuration) {
        SettableFuture<List<MessageView>> future = SettableFuture.create();
        if (!this.isRunning()) {
            LOGGER.error("Unable to receive message because simple consumer is not running, state={}, clientId={}",
                this.state(), clientId);
            future.setException(new IllegalStateException("Simple consumer is not running now"));
            return future;
        }
        final HashMap<String, FilterExpression> copy = new HashMap<>(subscriptionExpressions);
        final ArrayList<String> topics = new ArrayList<>(copy.keySet());
        // All topic is subscribed.
        if (topics.isEmpty()) {
            final IllegalArgumentException exception = new IllegalArgumentException("There is no topic to receive "
                + "message");
            future.setException(exception);
            return future;
        }
        final String topic = topics.get(IntMath.mod(topicIndex.getAndIncrement(), topics.size()));
        final FilterExpression filterExpression = copy.get(topic);
        final ListenableFuture<SubscriptionLoadBalancer> routeFuture = getSubscriptionTopicRouteResult(topic);
        final ListenableFuture<ReceiveMessageResult> future0 = Futures.transformAsync(routeFuture, result -> {
            final MessageQueueImpl mq = result.takeMessageQueue();
            final ReceiveMessageRequest request = wrapReceiveMessageRequest(maxMessageNum, mq, filterExpression,
                invisibleDuration);
            return receiveMessage(request, mq, awaitDuration);
        }, MoreExecutors.directExecutor());
        return Futures.transformAsync(future0, result -> Futures.immediateFuture(result.checkAndGetMessages()),
            clientCallbackExecutor);
    }

    /**
     * @see SimpleConsumer#ack(MessageView)
     */
    @Override
    public void ack(MessageView messageView) throws ClientException {
        final ListenableFuture<Void> future = ack0(messageView);
        handleClientFuture(future);
    }

    /**
     * @see SimpleConsumer#ackAsync(MessageView)
     */
    @Override
    public CompletableFuture<Void> ackAsync(MessageView messageView) {
        final ListenableFuture<Void> future = ack0(messageView);
        return FutureConverter.toCompletableFuture(future);
    }

    private ListenableFuture<Void> ack0(MessageView messageView) {
        SettableFuture<Void> future0 = SettableFuture.create();
        // Check consumer status.
        if (!this.isRunning()) {
            LOGGER.error("Unable to ack message because simple consumer is not running, state={}, clientId={}",
                this.state(), clientId);
            future0.setException(new IllegalStateException("Simple consumer is not running now"));
            return future0;
        }
        if (!(messageView instanceof MessageViewImpl)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Failed downcasting for "
                + "messageView");
            future0.setException(exception);
            return future0;
        }
        MessageViewImpl impl = (MessageViewImpl) messageView;
        final ListenableFuture<InvocationContext<AckMessageResponse>> future = ackMessage(impl);
        return Futures.transformAsync(future, context -> {
            final AckMessageResponse resp = context.getResp();
            final Status status = resp.getStatus();
            final Code code = status.getCode();
            switch (code) {
                case OK:
                    return Futures.immediateVoidFuture();
                case BAD_REQUEST:
                case ILLEGAL_TOPIC:
                case ILLEGAL_CONSUMER_GROUP:
                case INVALID_RECEIPT_HANDLE:
                case CLIENT_ID_REQUIRED:
                    throw new BadRequestException(code.getNumber(), status.getMessage());
                case UNAUTHORIZED:
                    throw new UnauthorizedException(code.getNumber(), status.getMessage());
                case FORBIDDEN:
                    throw new ForbiddenException(code.getNumber(), status.getMessage());
                case NOT_FOUND:
                case TOPIC_NOT_FOUND:
                    throw new NotFoundException(code.getNumber(), status.getMessage());
                case TOO_MANY_REQUESTS:
                    throw new TooManyRequestsException(code.getNumber(), status.getMessage());
                case INTERNAL_ERROR:
                case INTERNAL_SERVER_ERROR:
                    throw new InternalErrorException(code.getNumber(), status.getMessage());
                case PROXY_TIMEOUT:
                    throw new ProxyTimeoutException(code.getNumber(), status.getMessage());
                default:
                    throw new UnsupportedException(code.getNumber(), status.getMessage());
            }
        }, clientCallbackExecutor);
    }

    /**
     * @see SimpleConsumer#changeInvisibleDuration(MessageView, Duration)
     */
    @Override
    public void changeInvisibleDuration(MessageView messageView, Duration invisibleDuration) throws ClientException {
        final ListenableFuture<Void> future = changeInvisibleDuration0(messageView, invisibleDuration);
        handleClientFuture(future);
    }

    /**
     * @see SimpleConsumer#changeInvisibleDurationAsync(MessageView, Duration)
     */
    @Override
    public CompletableFuture<Void> changeInvisibleDurationAsync(MessageView messageView, Duration invisibleDuration) {
        final ListenableFuture<Void> future = changeInvisibleDuration0(messageView, invisibleDuration);
        return FutureConverter.toCompletableFuture(future);
    }

    public ListenableFuture<Void> changeInvisibleDuration0(MessageView messageView, Duration invisibleDuration) {
        SettableFuture<Void> future0 = SettableFuture.create();
        if (!(messageView instanceof MessageViewImpl)) {
            final IllegalArgumentException exception = new IllegalArgumentException("Failed downcasting for "
                + "messageView");
            future0.setException(exception);
            return future0;
        }
        MessageViewImpl impl = (MessageViewImpl) messageView;
        final ListenableFuture<InvocationContext<ChangeInvisibleDurationResponse>> future =
            changeInvisibleDuration(impl, invisibleDuration);
        return Futures.transformAsync(future, context -> {
            final ChangeInvisibleDurationResponse resp = context.getResp();
            // Refresh receipt handle manually.
            impl.setReceiptHandle(resp.getReceiptHandle());
            final Status status = resp.getStatus();
            final Code code = status.getCode();
            switch (code) {
                case OK:
                    return Futures.immediateVoidFuture();
                case BAD_REQUEST:
                case ILLEGAL_TOPIC:
                case ILLEGAL_CONSUMER_GROUP:
                case ILLEGAL_INVISIBLE_TIME:
                case INVALID_RECEIPT_HANDLE:
                case CLIENT_ID_REQUIRED:
                    throw new BadRequestException(code.getNumber(), status.getMessage());
                case UNAUTHORIZED:
                    throw new UnauthorizedException(code.getNumber(), status.getMessage());
                case NOT_FOUND:
                case TOPIC_NOT_FOUND:
                case TOO_MANY_REQUESTS:
                    throw new TooManyRequestsException(code.getNumber(), status.getMessage());
                case INTERNAL_ERROR:
                case INTERNAL_SERVER_ERROR:
                    throw new InternalErrorException(code.getNumber(), status.getMessage());
                case PROXY_TIMEOUT:
                    throw new ProxyTimeoutException(code.getNumber(), status.getMessage());
                default:
                    throw new UnsupportedException(code.getNumber(), status.getMessage());
            }
        }, MoreExecutors.directExecutor());
    }

    @Override
    public void close() {
        this.stopAsync().awaitTerminated();
    }

    @Override
    public ClientSettings getClientSettings() {
        return simpleConsumerSettings;
    }

    protected SimpleConsumerSettings getSimpleConsumerSettings() {
        return simpleConsumerSettings;
    }

    public void onTopicRouteDataResultUpdate0(String topic, TopicRouteDataResult topicRouteDataResult) {
        final SubscriptionLoadBalancer subscriptionLoadBalancer =
            new SubscriptionLoadBalancer(topicRouteDataResult);
        subTopicRouteDataResultCache.put(topic, subscriptionLoadBalancer);
    }

    private ListenableFuture<SubscriptionLoadBalancer> getSubscriptionTopicRouteResult(final String topic) {
        SettableFuture<SubscriptionLoadBalancer> future0 = SettableFuture.create();
        final SubscriptionLoadBalancer result = subTopicRouteDataResultCache.get(topic);
        if (null != result) {
            future0.set(result);
            return future0;
        }
        final ListenableFuture<TopicRouteDataResult> future = getRouteDataResult(topic);
        return Futures.transform(future, topicRouteDataResult -> {
            final SubscriptionLoadBalancer subscriptionLoadBalancer =
                new SubscriptionLoadBalancer(topicRouteDataResult);
            subTopicRouteDataResultCache.put(topic, subscriptionLoadBalancer);
            return subscriptionLoadBalancer;
        }, MoreExecutors.directExecutor());
    }
}

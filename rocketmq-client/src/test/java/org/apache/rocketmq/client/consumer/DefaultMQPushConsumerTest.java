package org.apache.rocketmq.client.consumer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.conf.BaseConfig;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.ClientException;
import org.apache.rocketmq.client.message.MessageExt;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultMQPushConsumerTest extends BaseConfig {

    private DefaultMQPushConsumer initPushConsumer(
            String consumerGroup, String nameServerAddr, String topic) throws ClientException {
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
//        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeStatus consumeMessage(
                            List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                        return ConsumeStatus.OK;
                    }
                });
        return consumer;
    }

    @Test
    public void testStartWithoutRegistration() throws ClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(dummyConsumerGroup);
        try {
            consumer.start();
            Assert.fail();
        } catch (ClientException ignore) {
            consumer.shutdown();
        }
    }

    @Test
    public void testStartWithoutSubscription() throws ClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(dummyConsumerGroup);
        consumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeStatus consumeMessage(
                            List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                        return ConsumeStatus.OK;
                    }
                });
        consumer.start();
        consumer.shutdown();
    }

    @Test
    public void testStartAndShutdown() throws ClientException {
        DefaultMQPushConsumer consumer =
                initPushConsumer(dummyConsumerGroup, dummyNameServerAddr, dummyTopic);
        consumer.start();
        consumer.shutdown();
    }

    @Test
    public void testStartRepeatedly() throws ClientException {
        DefaultMQPushConsumer consumer =
                initPushConsumer(dummyConsumerGroup, dummyNameServerAddr, dummyTopic);
        consumer.start();
        try {
            consumer.start();
            Assert.fail();
        } catch (ClientException e) {
            Assert.assertTrue(
                    e.getMessage().contains("The producer has attempted to be started before"));
        } finally {
            consumer.shutdown();
        }
    }

    @Test
    public void testSetGroupAfterStart() throws ClientException {
        DefaultMQPushConsumer consumer =
                initPushConsumer(dummyConsumerGroup, dummyNameServerAddr, dummyTopic);
        consumer.start();
        try {
            consumer.setConsumerGroup(dummyConsumerGroup);
            Assert.fail();
        } catch (Throwable e) {
            Assert.assertTrue(
                    e.getMessage().contains("Please set consumerGroup before consumer started"));
        } finally {
            consumer.shutdown();
        }
    }

    @Test
    public void testStartMultiConsumers() throws ClientException {
        {
            final DefaultMQPushConsumer consumer0 =
                    initPushConsumer(dummyConsumerGroup0, dummyNameServerAddr, dummyTopic);
            final DefaultMQPushConsumer consumer1 =
                    initPushConsumer(dummyConsumerGroup1, dummyNameServerAddr, dummyTopic);

            consumer0.start();
            consumer0.shutdown();
            consumer1.start();
            consumer1.shutdown();
        }
        {
            final DefaultMQPushConsumer consumer0 =
                    initPushConsumer(dummyConsumerGroup0, dummyNameServerAddr, dummyTopic);
            final DefaultMQPushConsumer consumer1 =
                    initPushConsumer(dummyConsumerGroup1, dummyNameServerAddr, dummyTopic);

            consumer0.start();
            consumer1.start();
            consumer0.shutdown();
            consumer1.shutdown();
        }
        {
            final DefaultMQPushConsumer consumer0 =
                    initPushConsumer(dummyConsumerGroup0, dummyNameServerAddr, dummyTopic);
            final DefaultMQPushConsumer consumer1 =
                    initPushConsumer(dummyConsumerGroup1, dummyNameServerAddr, dummyTopic);

            consumer0.start();
            consumer1.start();
            consumer0.shutdown();
            consumer1.shutdown();
        }
        {
            final DefaultMQPushConsumer consumer0 =
                    initPushConsumer(dummyConsumerGroup0, dummyNameServerAddr, dummyTopic);
            final DefaultMQPushConsumer consumer1 =
                    initPushConsumer(dummyConsumerGroup1, dummyNameServerAddr, dummyTopic);

            consumer0.start();
            consumer1.start();
            consumer1.shutdown();
            consumer0.shutdown();
        }
    }

    @Test(invocationCount = 16)
    public void testStartConsumersConcurrently() throws ClientException, InterruptedException {
        final DefaultMQPushConsumer consumer0 =
                initPushConsumer(dummyConsumerGroup0, dummyNameServerAddr, dummyTopic);
        final DefaultMQPushConsumer consumer1 =
                initPushConsumer(dummyConsumerGroup1, dummyNameServerAddr, dummyTopic);

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        final CountDownLatch latch = new CountDownLatch(2);
        final Runnable task0 =
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumer0.start();
                            Thread.sleep(10);
                            consumer0.shutdown();
                            latch.countDown();
                        } catch (Throwable ignore) {
                        }
                    }
                };

        final Runnable task1 =
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumer1.start();
                            Thread.sleep(10);
                            consumer1.shutdown();
                            latch.countDown();
                        } catch (Throwable ignore) {
                        }
                    }
                };

        final Random random = new Random();
        if (random.nextInt() % 2 == 0) {
            executor.submit(task0);
            executor.submit(task1);
        } else {
            executor.submit(task1);
            executor.submit(task0);
        }
        final boolean await = latch.await(8, TimeUnit.SECONDS);
        if (!await) {
            Assert.fail("Timeout to wait shutdown of consumer.");
        }
    }
}

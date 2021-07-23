package org.apache.rocketmq.client.impl.consumer;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.rocketmq.client.constant.ServiceState;
import org.apache.rocketmq.client.consumer.listener.ConsumeStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.ClientException;
import org.apache.rocketmq.client.message.MessageExt;
import org.apache.rocketmq.client.route.Partition;

public class ConsumeOrderlyService implements ConsumeService {
    private AtomicReference<ServiceState> state = new AtomicReference<ServiceState>(ServiceState.STARTING);
    private DefaultMQPushConsumerImpl impl;
    private MessageListenerOrderly messageListenerOrderly;

    public ConsumeOrderlyService(
            DefaultMQPushConsumerImpl impl, MessageListenerOrderly messageListenerOrderly) {
        this.impl = impl;
        this.messageListenerOrderly = messageListenerOrderly;
    }

    @Override
    public void start() throws ClientException {
        if (!state.compareAndSet(ServiceState.STARTED, ServiceState.STARTED)) {
            throw new ClientException("ConsumeOrderlyService has attempted to be started before");
        }
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void dispatch(ProcessQueue processQueue) {
    }

    @Override
    public ListenableFuture<ConsumeStatus> verifyConsumption(MessageExt messageExt, Partition partition) {
        return null;
    }
}

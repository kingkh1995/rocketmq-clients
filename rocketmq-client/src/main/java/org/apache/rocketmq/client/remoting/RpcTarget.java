package org.apache.rocketmq.client.remoting;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class RpcTarget {

    private final Endpoints endpoints;

    /**
     * Indicates that if gRPC's auto retry is enabled or not.
     */
    @EqualsAndHashCode.Exclude
    private final boolean autoRetryEnabled;
    @EqualsAndHashCode.Exclude
    private final boolean needHeartbeat;

    @Setter
    @EqualsAndHashCode.Exclude
    private volatile boolean isolated = false;

    public RpcTarget(Endpoints endpoints, boolean autoRetryEnabled, boolean needHeartbeat) {
        this.endpoints = endpoints;
        this.autoRetryEnabled = autoRetryEnabled;
        this.needHeartbeat = needHeartbeat;
    }
}

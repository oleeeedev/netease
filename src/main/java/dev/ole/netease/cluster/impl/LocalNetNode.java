package dev.ole.netease.cluster.impl;

import dev.ole.netease.NetAddress;
import dev.ole.netease.cluster.NetNode;
import dev.ole.netease.cluster.NetNodeConfig;
import dev.ole.netease.cluster.NetNodeData;
import dev.ole.netease.cluster.NetNodeState;
import dev.ole.netease.server.common.AbstractDynamicNetServer;
import dev.ole.netease.utils.NetFuture;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Accessors(fluent = true)
public final class LocalNetNode<D extends NetNodeData> extends AbstractDynamicNetServer<NetNodeConfig> implements NetNode<D> {

    private NetNodeState state = NetNodeState.UNAVAILABLE;
    private NetAddress address = new NetAddress(config().hostname(), config().port());

    private long lastDataUpdate;

    public LocalNetNode() {
        super(new NetNodeConfig());
    }

    @Override
    public String id() {
        return config().id();
    }

    @Override
    public D data() {
        return null;
    }

    @Override
    public void updateData() {
        // local node cannot be updated, only external nodes
        this.lastDataUpdate = System.currentTimeMillis();
    }

    @Override
    public @NotNull NetFuture<Void> boot() {
        var future = super.boot();
        future.whenCompleteSuccessfully(unused -> state = NetNodeState.READY);
        return future;
    }
}
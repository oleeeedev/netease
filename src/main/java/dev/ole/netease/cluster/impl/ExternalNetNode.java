package dev.ole.netease.cluster.impl;

import dev.ole.netease.NetAddress;
import dev.ole.netease.cluster.NetNode;
import dev.ole.netease.cluster.NetNodeData;
import dev.ole.netease.cluster.NetNodeState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class ExternalNetNode<D extends NetNodeData> implements NetNode<D> {

    private String id;
    private final NetAddress address;

    private NetNodeState state = NetNodeState.UNAVAILABLE;
    private long lastDataUpdate = -1;

    @Override
    public D data() {
        return null;
    }

    @Override
    public void updateData() {
        // todo sync data
        this.lastDataUpdate = System.currentTimeMillis();
    }

    @Override
    public long lastDataUpdate() {
        return lastDataUpdate;
    }
}
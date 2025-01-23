package dev.ole.netease.cluster.data;

import dev.ole.netease.cluster.NetNodeData;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class DefaultNetNodeData implements NetNodeData {

    private final long initializationTime = System.currentTimeMillis();

}
package dev.ole.netease;

import dev.ole.netease.client.NetClient;
import dev.ole.netease.client.impl.NetClientImpl;
import dev.ole.netease.cluster.NetCluster;
import dev.ole.netease.cluster.NetNodeData;
import dev.ole.netease.cluster.impl.NetClusterImpl;
import dev.ole.netease.server.NetServer;
import dev.ole.netease.server.impl.NetServerImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Net {

    @Contract(" -> new")
    @SuppressWarnings("java:S2440")
    public static @NotNull Net line() {
        return new Net();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetServer server() {
        return new NetServerImpl();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetClient client() {
        return new NetClientImpl();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetCluster<NetNodeData> cluster() {
        return new NetClusterImpl<>();
    }
}
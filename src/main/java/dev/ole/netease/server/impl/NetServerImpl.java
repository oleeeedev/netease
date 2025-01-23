package dev.ole.netease.server.impl;

import dev.ole.netease.server.NetServer;
import dev.ole.netease.server.NetServerConfig;
import dev.ole.netease.server.common.AbstractDynamicNetServer;

public final class NetServerImpl extends AbstractDynamicNetServer<NetServerConfig> implements NetServer {

    public NetServerImpl() {
        super(new NetServerConfig());
    }

}
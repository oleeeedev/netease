package dev.ole.netease.server;

import dev.ole.netease.NetComp;
import dev.ole.netease.tracking.TrackingProvider;

public interface NetServer extends NetComp<NetServerConfig>, TrackingProvider, NetServerClientHandler {

}
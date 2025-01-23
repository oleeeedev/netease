package dev.ole.netease.server;

import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.common.AbstractNetCompHandler;
import dev.ole.netease.server.common.AbstractDynamicNetServer;
import io.netty5.channel.Channel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class NetServerHandler extends AbstractNetCompHandler {

    private final AbstractDynamicNetServer<?> server;

    public NetServerHandler(AbstractDynamicNetServer<?> server) {
        super(server);
        this.server = server;
    }

    @Override
    public void netChannelClose(NetChannel channel) {
        this.server.unregisterChannel(channel);
    }

    @Override
    public void netChannelOpen(NetChannel channel) {
        if (!server.available()) {
            log.warn("Server is not available to register channel.");
            return;
        }

        server.registerChannel(channel);
    }

    @Override
    public NetChannel findChannel(Channel channel) {
        return server.allClients().stream().filter(it -> it.channel().equals(channel)).findFirst().orElse(null);
    }
}
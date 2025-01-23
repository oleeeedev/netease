package dev.ole.netease.channel.common;

import dev.ole.netease.NetAddress;
import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.packet.Packet;
import dev.ole.netease.request.NetRequestPool;
import dev.ole.netease.utils.NetFuture;
import io.netty5.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Log4j2
@Accessors(fluent = true)
public abstract class AbstractNetChannel implements NetChannel {

    private String id;
    private final Channel channel;
    private final NetAddress clientAddress;

    public AbstractNetChannel(@NotNull Channel channel) {
        this.channel = channel;
        this.clientAddress = NetAddress.fromAddress(channel.localAddress());
    }

    @Override
    public boolean available() {
        return channel != null && channel.isActive() && clientAddress != null && id != null;
    }

    @Override
    public void send(Packet packet) {
        if(!available()) {
            log.warn("Channel is not available to send packet.");
            return;
        }
        log.debug("Sending packet to channel: {}", packet);
        channel().writeAndFlush(packet);
    }

    @Override
    public NetFuture<Void> close() {
        return NetFuture.interpretFuture(channel.close());
    }

    @Override
    public void updateId(String id) {
        this.id = id;
        // todo call maybe on a valid connection
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractNetChannel netChannel && netChannel.channel.equals(channel) && (netChannel.id == null || netChannel.id.equals(id));
    }

    @Override
    public void callRequest(NetChannel channel, UUID requestId, Packet response) {
        NetRequestPool.applyRequest(requestId, channel, response);
    }
}
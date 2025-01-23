package dev.ole.netease.client;

import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.client.common.AbstractNetClient;
import dev.ole.netease.common.AbstractNetCompHandler;
import dev.ole.netease.packet.Packet;
import dev.ole.netease.request.RequestScheme;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
public final class NetClientHandler extends AbstractNetCompHandler {

    private final AbstractNetClient client;

    public NetClientHandler(AbstractNetClient client) {
        super(client);
        this.client = client;
    }

    @Override
    public void messageReceived(@NotNull ChannelHandlerContext ctx, Packet packet) {
        log.info("Packt kommt an: " + packet.getClass().getSimpleName());
        super.messageReceived(ctx, packet);
    }

    @Override
    public void netChannelClose(NetChannel channel) {
        client.close();
    }

    @Override
    public void netChannelOpen(@NotNull NetChannel channel) {
        client.channel(channel.channel());

        if(client.bootFuture() == null) {
            log.warn("Client boot future is null. So we can't proceed with the channel open event.");
            channel.close();
            return;
        }

        channel.updateId(client.config().id);
        client.request(RequestScheme.CLIENT_AUTH).send(client.config().id).whenComplete((result, throwable) -> {
            if (result) {
                client.available(true);
                client.bootFuture().complete();
            }
        });
    }

    @Override
    public NetChannel findChannel(Channel channel) {
        return this.client;
    }
}
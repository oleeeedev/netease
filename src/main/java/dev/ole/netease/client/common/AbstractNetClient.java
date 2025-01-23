package dev.ole.netease.client.common;

import dev.ole.netease.NetAddress;
import dev.ole.netease.NetCompHandler;
import dev.ole.netease.broadcast.Broadcast;
import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.channel.NetChannelInitializer;
import dev.ole.netease.client.NetClient;
import dev.ole.netease.client.NetClientBroadcast;
import dev.ole.netease.client.NetClientConfig;
import dev.ole.netease.client.NetClientHandler;
import dev.ole.netease.common.AbstractNetComp;
import dev.ole.netease.packet.Packet;
import dev.ole.netease.request.NetRequest;
import dev.ole.netease.request.NetRequestPool;
import dev.ole.netease.request.RequestScheme;
import dev.ole.netease.request.impl.Request;
import dev.ole.netease.utils.NetFuture;
import dev.ole.netease.utils.NetworkNettyUtils;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelOption;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Setter
@Getter
@Accessors(fluent = true)
@Log4j2
public abstract class AbstractNetClient extends AbstractNetComp<NetClientConfig> implements NetClient {

    @Nullable
    @Setter
    private Channel channel;

    @Nullable
    private NetFuture<Void> bootFuture;

    public AbstractNetClient() {
        super(new NetClientConfig());
    }

    @Override
    public NetFuture<Void> boot() {
        this.bootFuture = new NetFuture<>();
        new Bootstrap()
                .group(mainGroup())
                .channelFactory(NetworkNettyUtils::createChannelFactory)
                .handler(new NetChannelInitializer(handler()))
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.IP_TOS, 24)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .connect(config().hostname(), config().port()).addListener(future -> {
                    // we wait for the identification -> success future
                    if(future.isFailed() && bootFuture != null) {
                        this.bootFuture.completeExceptionally(future.cause());
                    }
                });

        return this.bootFuture;
    }

    @Override
    public NetCompHandler handler() {
        return new NetClientHandler(this);
    }

    @Override
    public <R, A> NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return new Request<>(id, this);
    }

    @Override
    public void send(Packet packet) {
        this.channel.writeAndFlush(packet);
    }

    @Override
    public NetAddress clientAddress() {
        return null;
    }

    @Override
    public void updateId(String id) {
        this.config().id = id;
    }

    @Override
    public String id() {
        return this.config().id;
    }

    @Override
    public Broadcast broadcast() {
        return new NetClientBroadcast(this, this);
    }

    @Override
    public void callRequest(NetChannel channel, UUID requestId, Packet response) {
        NetRequestPool.applyRequest(requestId, channel, response);
    }
}
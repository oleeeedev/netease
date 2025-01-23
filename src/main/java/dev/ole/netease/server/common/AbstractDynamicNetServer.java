package dev.ole.netease.server.common;

import dev.ole.netease.NetCompHandler;
import dev.ole.netease.NetConfig;
import dev.ole.netease.broadcast.Broadcast;
import dev.ole.netease.broadcast.impl.BroadcastImpl;
import dev.ole.netease.broadcast.packets.BroadcastPacket;
import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.channel.NetChannelInitializer;
import dev.ole.netease.common.AbstractNetComp;
import dev.ole.netease.request.RequestScheme;
import dev.ole.netease.server.NetServerClientHandler;
import dev.ole.netease.server.NetServerHandler;
import dev.ole.netease.utils.NetFuture;
import dev.ole.netease.utils.NetworkNettyUtils;
import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.EventLoopGroup;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Log4j2
public abstract class AbstractDynamicNetServer<C extends NetConfig> extends AbstractNetComp<C> implements NetServerClientHandler {

    private final static int NET_SERVER_GROUP_THREADS = 1;

    private final Collection<NetChannel> clients = new LinkedList<>();
    private final EventLoopGroup workerGroup = NetworkNettyUtils.createEventLoopGroup(0);

    public AbstractDynamicNetServer(C config) {
        super(config, NET_SERVER_GROUP_THREADS);

        this.waitFor(RequestScheme.CLIENT_AUTH, (id, channel) -> {
            // set channel name here
            // check security rules here
            channel.updateId(id);

            log.debug("Client {} has been authenticated", id);
            return true;
        });

        track(BroadcastPacket.class, (channel, tracking) -> {
            this.call(channel, tracking.packet());

            // redirect to other clients
            for (var client : availableClients()) {
                if(client.equals(channel)) {
                    // we're not sending the server his self the packet
                    continue;
                }
                // here we need only the content for the client. Not the broadcast packet.
                channel.send(tracking.packet());
            }
        });
    }

    @Override
    public NetFuture<Void> boot() {
        var future = NetFuture.interpretFuture(new ServerBootstrap()
                .group(mainGroup(), this.workerGroup)
                .childHandler(new NetChannelInitializer(handler()))
                .channelFactory(NetworkNettyUtils.generateChannelFactory())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.IP_TOS, 24)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(config().hostname(), config().port()));

        future.whenCompleteSuccessfully(it -> {
            // now we can allow connections
            this.available(true);
            log.debug("The server is now running on port {}", config().port());
        });

        return future;
    }

    @Override
    public NetFuture<Void> close() {
        log.debug("Closing the server...");

        var future = new NetFuture<Void>();

        CompletableFuture.allOf(clients.stream().map(NetChannel::close).toArray(value -> new CompletableFuture[clients.size()]))
                .whenComplete((unused, throwable) -> super.close().waitFor(this.workerGroup.shutdownGracefully())
                        .whenComplete((it, th) -> future.complete()));

        return future;
    }

    @Override
    public NetCompHandler handler() {
        return new NetServerHandler(this);
    }

    public void registerChannel(NetChannel channel) {
        this.clients.add(channel);
    }

    public void unregisterChannel(NetChannel channel) {
        this.clients.remove(channel);
    }

    public Collection<NetChannel> allClients() {
        return Collections.unmodifiableCollection(clients);
    }

    public int amountOfClients() {
        return this.clients.size();
    }

    @Override
    public Broadcast broadcast() {
        return new BroadcastImpl(this, this.availableClients());
    }
}
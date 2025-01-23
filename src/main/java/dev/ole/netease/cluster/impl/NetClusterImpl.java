package dev.ole.netease.cluster.impl;

import dev.ole.netease.NetAddress;
import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.cluster.NetCluster;
import dev.ole.netease.cluster.NetNode;
import dev.ole.netease.cluster.NetNodeData;
import dev.ole.netease.utils.NetFuture;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@Getter
@Accessors(fluent = true)
public class NetClusterImpl<D extends NetNodeData> implements NetCluster<D> {

    private static final Logger log = LogManager.getLogger(NetClusterImpl.class);
    private NetNode<D> headNode;
    private final LocalNetNode<D> localNode;

    private final Collection<NetNode<D>> nodes = new ArrayList<>();
    private final Collection<NetChannel> clients = new ArrayList<>();

    public NetClusterImpl() {
        // todo‚
        this.localNode = new LocalNetNode<>();
    }

    @Override
    public void searchHeadNode() {
        if(this.nodes.isEmpty()) {
            this.headNode = this.localNode;
            return;
        }
        // Find the head node by the starting time.
        // The oldest node are the head node
        this.headNode = this.nodes.stream()
                .min(Comparator.comparingLong(value -> value.data().initializationTime()))
                .orElseThrow();
    }

    @Override
    public void registerNode(NetAddress address) {
        if (inCluster(address)) {
            log.warn("You try to add a duplicated node entry into your cluster!");
            return;
        }
        var node = new ExternalNetNode<D>(address);
        this.nodes.add(node);

        // todo bind alle here
    }

    @Override
    public void unregisterNode(NetNode<D> node) {

    }

    @Override
    public boolean available() {
        return this.localNode.available();
    }

    @Override
    public NetFuture<Void> close() {
        var future = new NetFuture<Void>();

        // todo find a better not duplicated logic
        this.localNode.close().whenComplete((unused, throwable) -> {
            if(throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                future.complete();
            }
        });

        return future;
    }

    @Override
    public NetFuture<Void> boot() {
        var future = new NetFuture<Void>();

        this.searchHeadNode();

        //todo add other nodes
        //todo search the new head node
        // todo find a better not duplicated logic
        localNode.boot().whenComplete((unused, throwable) -> {
            if(throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                future.complete();
            }
        });

        return future;
    }

    @Override
    public int amountOfClients() {
        return this.clients.size();
    }

    @Override
    public Collection<NetChannel> allClients() {
        return Collections.unmodifiableCollection(this.clients);
    }

    private boolean inCluster(NetAddress address) {
        return this.nodes.stream().anyMatch(it -> it.address().equals(address));
    }
}
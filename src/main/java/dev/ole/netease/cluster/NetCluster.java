package dev.ole.netease.cluster;

import dev.ole.netease.Available;
import dev.ole.netease.Bootable;
import dev.ole.netease.Closeable;
import dev.ole.netease.NetAddress;
import dev.ole.netease.cluster.impl.LocalNetNode;
import dev.ole.netease.server.NetServerClientHandler;

import java.util.Collection;

public interface NetCluster<D extends NetNodeData> extends Available, Closeable, Bootable, NetServerClientHandler {

    /**
     * Get the head node of the cluster.
     * @return the head node of the cluster.
     */
    NetNode<D> headNode();

    /**
     * Search for the head node of the cluster.
     */
    void searchHeadNode();

    /**
     * Get the local runtime representing node
     * @return the node
     */
    LocalNetNode<D> localNode();

    /**
     * Add a new node into the networking cluster
     * @param address the new node address
     */
    void registerNode(NetAddress address);

    /**
     * Unregister a connected node
     * @param node the current cluster node
     */
    void unregisterNode(NetNode<D> node);

    /**
     * Get all nodes of the cluster
     * @return a list of nodes
     */
    Collection<NetNode<D>> nodes();




}
package dev.ole.netease.cluster;

import dev.ole.netease.NetAddress;

public interface NetNode<D extends NetNodeData> {

    /**
     *  Get the id of the node
     * @return the id
     */
    String id();

    /**
     * Get the current data of the node
     * @return the data
     */
    D data();

    /**
     * Request data update process
     */
    void updateData();

    /**
     * Get the timestamp of the last update
     * @return the time millis
     */
    long lastDataUpdate();

    /**
     * Current state of the node
     * @return the state
     */
    NetNodeState state();

    /**
     * Get the address of the node
     * @return the address
     */
    NetAddress address();

}
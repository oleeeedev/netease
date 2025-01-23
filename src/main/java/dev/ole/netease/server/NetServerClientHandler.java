package dev.ole.netease.server;

import dev.ole.netease.Available;
import dev.ole.netease.channel.NetChannel;

import java.util.Collection;

public interface NetServerClientHandler {

    /**
     * Get the amount of all connected clients. Ignore availability
     * @return the amount
     */
    int amountOfClients();

    /**
     * Return a list of all clients. Ignore availability
     * @return a collection of channels
     */
    Collection<NetChannel> allClients();

    /**
     * Return a list of only available clients.
     * @return a collection of channel
     */
    default Collection<NetChannel> availableClients() {
        return allClients().stream().filter(Available::available).toList();
    }
}
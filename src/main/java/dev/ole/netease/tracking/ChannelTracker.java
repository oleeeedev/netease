package dev.ole.netease.tracking;

import dev.ole.netease.channel.NetChannel;

public interface ChannelTracker <A extends Tracking> {

    void track(NetChannel channel, A tracking);

    @SuppressWarnings("unchecked")
    default void trackWith(NetChannel channel, Tracking tracking) {
        this.track(channel, (A) tracking);
    }
}
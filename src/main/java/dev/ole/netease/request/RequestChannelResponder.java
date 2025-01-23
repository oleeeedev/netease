package dev.ole.netease.request;

import dev.ole.netease.channel.NetChannel;

public interface RequestChannelResponder<T, R> {

    R respond(T request, NetChannel channel);

    @SuppressWarnings("unchecked")
    default R respondWith(Object request, NetChannel channel) {
        return respond((T) request, channel);
    }
}
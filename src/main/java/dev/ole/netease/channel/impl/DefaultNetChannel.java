package dev.ole.netease.channel.impl;

import dev.ole.netease.channel.common.AbstractNetChannel;
import dev.ole.netease.request.NetRequest;
import dev.ole.netease.request.RequestScheme;
import dev.ole.netease.request.impl.Request;
import io.netty5.channel.Channel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class DefaultNetChannel extends AbstractNetChannel {

    public DefaultNetChannel(Channel channel) {
        super(channel);
    }

    @Contract("_ -> new")
    @Override
    public <R, A> @NotNull NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return new Request<>(id, this);
    }
}
package dev.ole.netease.request;

import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface RequestProvider {

    /**
     * Call a request with a response
     * @param channel the channel
     * @param requestId the request id
     * @param response the response
     */
    void callRequest(NetChannel channel, UUID requestId, Packet response);

    /**
     * Request a specific request id
     * @param id the request id
     * @return the request
     * @param <R> the request type
     */
    <R, A> NetRequest<R, A> request(@NotNull RequestScheme<R, A> id);

}
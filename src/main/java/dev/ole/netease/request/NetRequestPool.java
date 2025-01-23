package dev.ole.netease.request;

import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.packet.Packet;
import dev.ole.netease.packet.basic.BooleanPacket;
import dev.ole.netease.request.impl.Request;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@UtilityClass
public final class NetRequestPool {

    private final Map<UUID, Request<?, ?>> requests = new HashMap<>();

    public void put(Request<?, ?> request) {
        requests.put(request.id(), request);
    }

    public void applyRequest(UUID id, NetChannel channel, Packet response) {
        if (!requests.containsKey(id)) {
            log.warn("No request found for id: {}", id);
            return;
        }

        var request = requests.get(id);
        requests.remove(id);

        if (response.getClass().equals(request.requestScheme().responseType())) {
            request.complete(response);
        } else if (response instanceof BooleanPacket booleanPacket) {
            request.complete(booleanPacket.value());
        } else {
            System.out.println("Request completed!!!!");
        }
        //todo other values
    }

    public int amountOfRequests() {
        return requests.size();
    }

    public void removeExceptionallyRequest(UUID id) {
        requests.get(id).completeFuture().completeExceptionally(new Exception("Request failed"));
        requests.remove(id);
    }
}
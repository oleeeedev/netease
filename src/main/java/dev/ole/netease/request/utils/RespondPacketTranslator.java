package dev.ole.netease.request.utils;

import dev.ole.netease.exceptions.SimpleResponderTypeException;
import dev.ole.netease.packet.Packet;
import dev.ole.netease.packet.basic.BooleanPacket;
import dev.ole.netease.packet.basic.StringPacket;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class RespondPacketTranslator {

    public Packet translate(Object value) throws SimpleResponderTypeException {

        if (value instanceof Packet packet) {
            return packet;
        }

        if (value instanceof Boolean booleanValue) {
            return new BooleanPacket(booleanValue);
        }

        if (value instanceof String stringValue) {
            return new StringPacket(stringValue);
        }

        throw new SimpleResponderTypeException(value);
    }

    public Object translatePacket(Object value) {

        if (value instanceof StringPacket packet) {
            return packet.getValue();
        }

        if (value instanceof BooleanPacket packet) {
            return packet.value();
        }

        return value;
    }
}
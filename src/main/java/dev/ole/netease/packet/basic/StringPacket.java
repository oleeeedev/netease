package dev.ole.netease.packet.basic;

import dev.ole.netease.packet.Packet;
import dev.ole.netease.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class StringPacket extends Packet {

    private String value;

    @Override
    public void read(@NotNull PacketBuffer buffer) {
        value = buffer.readString();
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(value);
    }
}
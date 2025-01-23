package dev.ole.netease.request.packets;

import dev.ole.netease.packet.Packet;
import dev.ole.netease.packet.PacketAllocator;
import dev.ole.netease.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public final class ResponsePacket extends Packet {

    private UUID id;
    private Packet packet;

    @Override
    @SneakyThrows
    public void read(@NotNull PacketBuffer buffer) {
        id = buffer.readUniqueId();

        var className = buffer.readString();
        this.packet = (Packet) PacketAllocator.allocate(Class.forName(className));

        packet.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeUniqueId(id);

        buffer.writeString(packet.getClass().getName());
        packet.write(buffer);
    }
}
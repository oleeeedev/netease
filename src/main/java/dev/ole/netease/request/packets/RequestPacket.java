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
public final class RequestPacket extends Packet {

    private String requestId;
    private UUID id;
    private Packet request;

    @Override
    @SneakyThrows
    public void read(@NotNull PacketBuffer buffer) {
        this.requestId = buffer.readString();
        this.id = buffer.readUniqueId();

        var className = buffer.readString();
        this.request = (Packet) PacketAllocator.allocate(Class.forName(className));
        request.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(this.requestId);
        buffer.writeUniqueId(this.id);

        buffer.writeString(this.request.getClass().getName());
        this.request.write(buffer);
    }
}
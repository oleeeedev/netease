package dev.ole.netease.broadcast.packets;

import dev.ole.netease.packet.Packet;
import dev.ole.netease.packet.basic.HoldingPacket;

public final class BroadcastPacket extends HoldingPacket {

    public BroadcastPacket(Packet packet) {
        super(packet);
    }
}
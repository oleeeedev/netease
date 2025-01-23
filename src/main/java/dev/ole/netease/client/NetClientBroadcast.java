package dev.ole.netease.client;

import dev.ole.netease.NetComp;
import dev.ole.netease.broadcast.impl.BroadcastImpl;
import dev.ole.netease.broadcast.packets.BroadcastPacket;
import dev.ole.netease.channel.NetChannel;
import dev.ole.netease.packet.Packet;

import java.util.List;

public final class NetClientBroadcast extends BroadcastImpl {

    public NetClientBroadcast(NetComp<?> comp, NetChannel channel) {
        super(comp, List.of(channel));
    }

    @Override
    public void send(Packet packet) {
        super.send(new BroadcastPacket(packet));
    }
}
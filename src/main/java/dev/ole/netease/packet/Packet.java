package dev.ole.netease.packet;

import dev.ole.netease.tracking.Tracking;

public abstract class Packet implements Tracking {

    public abstract void read(PacketBuffer buffer);

    public abstract void write(PacketBuffer buffer);

}
package dev.ole.netease.broadcast;

import dev.ole.netease.packet.Packet;

public interface Broadcast {

    void send(Packet packet);

    Broadcast toAll();

    //todo maybe a better naming
    Broadcast toMe();

}
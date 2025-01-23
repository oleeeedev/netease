package dev.ole.netease.tracking;

public interface Tracker<A extends Tracking> {

    void track(A tracking);

}
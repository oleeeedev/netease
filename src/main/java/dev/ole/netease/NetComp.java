package dev.ole.netease;

import dev.ole.netease.tracking.TrackingProvider;

/**
 * A network component.
 */
public interface NetComp<C extends NetConfig> extends Available, TrackingProvider, Closeable, Broadcastable, Bootable {

    /**
     * Get the configuration of the component.
     * @return the configuration of the component.
     */
    C config();

    /**
     * Get the handler of the component.
     * @return the handler of the component.
     */
    NetCompHandler handler();

}
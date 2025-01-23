package dev.ole.netease;

public interface Available {

    /**
     * Check if the component is available. Packets can be sent and received .
     * @return true if the component is available, false otherwise.
     */
    boolean available();

}
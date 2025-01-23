package dev.ole.netease;

import dev.ole.netease.utils.NetFuture;

public interface Closeable {

    NetFuture<Void> close();

}
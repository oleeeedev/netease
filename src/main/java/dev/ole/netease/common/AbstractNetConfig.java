package dev.ole.netease.common;

import dev.ole.netease.NetConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractNetConfig implements NetConfig {

    private String hostname = "localhost";
    private int port = 9090;

    @Override
    public void hostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void port(int port) {
        this.port = port;
    }
}
package dev.ole.netease.cluster;

import dev.ole.netease.common.AbstractNetConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public final class NetNodeConfig extends AbstractNetConfig {

    private String id;

    public NetNodeConfig() {
        id = UUID.randomUUID().toString();
    }
}
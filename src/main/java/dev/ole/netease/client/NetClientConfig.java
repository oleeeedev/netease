package dev.ole.netease.client;

import dev.ole.netease.common.AbstractNetConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(fluent = true)
public final class NetClientConfig extends AbstractNetConfig {

    public String id = UUID.randomUUID().toString();

}
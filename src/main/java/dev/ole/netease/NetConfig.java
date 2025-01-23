package dev.ole.netease;

public interface NetConfig {

    String hostname();

    int port();

    void hostname(String hostname);

    void port(int port);

}
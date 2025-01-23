package dev.ole.netease.request;

public interface RequestResponder<T, R> {

    R respond(T request);

}
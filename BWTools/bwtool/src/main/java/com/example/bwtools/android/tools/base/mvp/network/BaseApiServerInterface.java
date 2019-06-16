package com.example.bwtools.android.tools.base.mvp.network;

public interface BaseApiServerInterface<T> extends BaseNetworkInterface<T> {
    T handleResponse();
}

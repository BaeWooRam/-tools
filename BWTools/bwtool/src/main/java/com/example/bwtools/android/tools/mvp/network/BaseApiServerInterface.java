package com.example.bwtools.android.tools.mvp.network;

public interface BaseApiServerInterface<T> extends BaseNetworkInterface<T>{
    T handleResponse();
}

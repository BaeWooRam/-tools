package com.example.bwtools.android.tools.base.mvp.network;

public interface BaseNetworkInterface<T> {
    boolean checkNullData(T data);
    void handleError(String ErrorCode);
}

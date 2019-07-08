package com.example.bwtools.android.tools.mvp.network;

public interface BaseNetworkInterface<T> {
    boolean checkNullData(T data);
    void handleError(String ErrorCode);
}

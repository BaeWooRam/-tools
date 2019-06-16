package com.example.bwtools.android.tools.base.mvp.network;

import com.example.bwtools.android.tools.base.BaseJson;

public interface BaseRetrofitInterface<T,CallType extends BaseJson> extends BaseNetworkInterface<T> {
    T handleResponse(retrofit2.Response<CallType> response);
}

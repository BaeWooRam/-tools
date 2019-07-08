package com.example.bwtools.android.tools.mvp.network;

import com.example.bwtools.android.tools.mvp.base.BaseJsonPage;

public interface BaseRetrofitPageInterface<T,CallType extends BaseJsonPage> extends BaseNetworkInterface<T> {
    T handleResponse(retrofit2.Response<CallType> response);
}

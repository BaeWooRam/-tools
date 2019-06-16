package com.example.bwtools.android.tools.base.mvp.network;

import com.example.bwtools.android.tools.base.BaseJsonPage;

public interface BaseRetrofitPageInterface<T,CallType extends BaseJsonPage> extends BaseNetworkInterface<T> {
    T handleResponse(retrofit2.Response<CallType> response);
}

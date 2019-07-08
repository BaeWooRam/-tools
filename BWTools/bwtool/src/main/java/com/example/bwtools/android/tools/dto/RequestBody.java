package com.example.bwtools.android.tools.dto;


public class RequestBody extends BaseRequest {
    public RequestBody(String queryKey, String queryValue) {
        this.setKey(queryKey);
        this.setValue(queryValue);
    }

    public RequestBody(String queryKey, String queryValue, boolean valueEncode) {
        this.setKey(queryKey);
        this.setValue(queryValue);
    }
}
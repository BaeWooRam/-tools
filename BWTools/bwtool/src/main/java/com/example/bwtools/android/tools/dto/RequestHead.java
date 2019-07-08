package com.example.bwtools.android.tools.dto;


public class RequestHead extends BaseRequest {
    public RequestHead(String queryKey, String queryValue) {
        this.setKey(queryKey);
        this.setValue(queryValue);
    }

    public RequestHead(String queryKey, String queryValue, boolean valueEncode) {
        this.setKey(queryKey);
        this.setValue(queryValue);
    }
}
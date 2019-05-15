package com.example.bwtools.android.tools.base.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestQuery extends BaseRequest {
    private boolean valueEncode = false;

    public RequestQuery(String queryKey, String queryValue) {
        this.setKey(queryKey);
        this.setValue(queryValue);
        this.valueEncode = false;
    }

    public RequestQuery(String queryKey, String queryValue, boolean valueEncode) {
        this.setKey(queryKey);
        this.setValue(queryValue);
        this.valueEncode = valueEncode;
    }

    public boolean isValueEncode() {
        return this.valueEncode;
    }

    public void setValueEncode(boolean valueEncode) {
        this.valueEncode = valueEncode;
    }

    public String getQuery(){
        if(valueEncode){
            try {
                return URLEncoder.encode(getValue(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
            return getValue();
    }
}
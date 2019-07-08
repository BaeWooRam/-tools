package com.example.bwtools.android.tools.dto;

public class BaseRequest {
    private String Key;
    private String Value;

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }
}

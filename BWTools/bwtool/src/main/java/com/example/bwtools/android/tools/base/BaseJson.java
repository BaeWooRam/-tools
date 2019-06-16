package com.example.bwtools.android.tools.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseJson {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

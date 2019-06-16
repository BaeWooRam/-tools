package com.example.bwtools.android.tools.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseJsonPage {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("page_now")
    @Expose
    private int pageNow;

    @SerializedName("page_total")
    @Expose
    private int pageTotal;

    public int getTotal() {
        return total;
    }

    public int getPageNow() {
        return pageNow;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

package com.example.bwtools.android.tools.dto;

import java.io.Serializable;

public class KaKaORegion extends Location implements Serializable {
    private String internetURL;

    public String getInternetURL() {
        return internetURL;
    }

    public void setInternetURL(String internetURL) {
        this.internetURL = internetURL;
    }
}

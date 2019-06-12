package com.example.bwtools.android.tools.base.dto;

import com.google.gson.annotations.SerializedName;

public class EventAndAd {
    @SerializedName("event_num")
    private int num;

    @SerializedName("event_image")
    private String image;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

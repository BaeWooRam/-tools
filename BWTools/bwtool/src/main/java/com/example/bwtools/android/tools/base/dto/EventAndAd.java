package com.example.bwtools.android.tools.base.dto;

import com.google.gson.annotations.SerializedName;

public class EventAndAd {
    @SerializedName("event_num")
    private int num;

    @SerializedName("event_thumbnail")
    private String imageThumbnail;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String image) {
        this.imageThumbnail = image;
    }
}

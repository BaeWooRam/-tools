package com.example.bwtools.android.builder.naverapi;

import com.google.gson.annotations.SerializedName;

public class NaverRegion {
    @SerializedName("title")
    private String title;

    @SerializedName("address")
    private String address;

    @SerializedName("category")
    private String category;

    @SerializedName("link")
    private String internetURL;

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInternetURL() {
        return internetURL;
    }

    public void setInternetURL(String internetURL) {
        this.internetURL = internetURL;
    }
}

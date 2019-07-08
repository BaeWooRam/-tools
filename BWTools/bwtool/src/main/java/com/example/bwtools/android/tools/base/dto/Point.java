package com.example.bwtools.android.tools.base.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Point implements Serializable {
    @SerializedName("x")
    private double longitude;

    @SerializedName("y")
    private double latitude;

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

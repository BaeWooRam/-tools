package com.example.bwtools.android.tools.base.dto;

public class Location {
    private Point locationPoint;

    public Location(Point locationPoint) {
        this.locationPoint = locationPoint;
    }

    public void setLocationPoint(Point locationPoint) {
        this.locationPoint = locationPoint;
    }

    public String getLocationLongitude(){
        return String.valueOf(locationPoint.getLongitude());
    }

    public String getLocationLatitude(){
        return String.valueOf(locationPoint.getLatitude());
    }
}

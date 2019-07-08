package com.example.bwtools.android.tools.base.dto;

import java.io.Serializable;

public class Location implements Serializable {
    private String num;
    private String name;
    private String address;
    private String phone;
    private String image;
    private String category;
    private String information;
    private Point locationPoint;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setLocationPoint(Point locationPoint) {
        this.locationPoint = locationPoint;
    }

    public String getLocationLongitude(){
        return String.valueOf(locationPoint.getLongitude());
    }

    public double getLongitude(){
        return locationPoint.getLongitude();
    }

    public String getLocationLatitude(){
        return String.valueOf(locationPoint.getLatitude());
    }

    public double getLatitude(){
        return locationPoint.getLatitude();
    }

}

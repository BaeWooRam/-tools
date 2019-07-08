package com.example.bwtools.android.tools.dto;

public class Rect {
    private Point leftPoint, rightPoint;

    public Rect(Point leftPoint, Point rightPoint) {
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
    }

    public void setLeftPoint(Point leftPoint) {
        this.leftPoint = leftPoint;
    }

    public void setRightPoint(Point rightPoint) {
        this.rightPoint = rightPoint;
    }

    public String getLeftLongitude(){
        return String.valueOf(leftPoint.getLongitude());
    }

    public String getLeftLatitude(){
        return String.valueOf(leftPoint.getLatitude());
    }

    public String getRightLongitude(){
        return String.valueOf(rightPoint.getLongitude());
    }

    public String getRightLatitude(){
        return String.valueOf(rightPoint.getLatitude());
    }
}

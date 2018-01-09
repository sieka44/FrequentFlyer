package com.sabre.frequentflyer.api;

public class Coordinates {
    private String name;
    private double latitude;
    private double longtitude;

    Coordinates(String name, double latitude, double longtitude ){
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}

package com.sabre.frequentflyer.api;

public class GeoCodeRQ {
    String cityName;
    public GeoCodeRQ(String cityName){
        this.cityName = cityName;
    }
    @Override
    public String toString() {
        return "{\n" +
                    " \"GeoCodeRQ\":{\r\n    " +
                        "\"PlaceById\":{\r\n      \"Id\":\""+cityName+"\",\r\n      " +
                        "\"BrowseCategory\": " +
                            "{\r\n        " +
                            "\"name\": \"AIR\"\r\n      " +
                            "}\r\n    " +
                        "}\r\n  " +
                    "}\n" +
                "}";
    }
}

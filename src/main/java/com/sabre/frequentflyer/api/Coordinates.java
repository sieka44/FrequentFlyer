package com.sabre.frequentflyer.api;

import lombok.Data;

@Data
public class Coordinates {
    /**
     * -- GETTER --
     * Returns <code>String</code> with three-letter identifier of airport.
     * -- SETTER --
     * Changes the identifier.
     */
    private String name;
    /**
     * -- GETTER --
     * Returns <code>double</code> latitude.
     * -- SETTER --
     * Sets the latitude.
     */
    private double latitude;
    /**
     * -- GETTER --
     * Returns <code>double</code> longitude.
     * -- SETTER --
     * Sets the longitude.
     */
    private double longitude;

    Coordinates(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

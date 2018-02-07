package com.sabre.frequentflyer.api;

public class Coordinates {
    /**
     * Id of city on current coordinates.
     */
    private String name;
    /**
     * Latitude.
     */
    private double latitude;
    /**
     * Longitude.
     */
    private double longitude;

    Coordinates(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    /**
     * -- GETTER --
     * Returns <code>String</code> with three-letter identifier of airport.
     */
    public String getName() {
        return this.name;
    }
    /**
     * -- GETTER --
     * Returns <code>double</code> latitude.
     */
    public double getLatitude() {
        return this.latitude;
    }
    /**
     * -- GETTER --
     * Returns <code>double</code> longitude.
     */
    public double getLongitude() {
        return this.longitude;
    }
    /**
     * -- SETTER --
     * Changes the identifier.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * -- SETTER --
     * Sets the latitude.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    /**
     * -- SETTER --
     * Sets the longitude.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Coordinates)) return false;
        final Coordinates other = (Coordinates) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (Double.compare(this.getLatitude(), other.getLatitude()) != 0) return false;
        if (Double.compare(this.getLongitude(), other.getLongitude()) != 0) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final long $latitude = Double.doubleToLongBits(this.getLatitude());
        result = result * PRIME + (int) ($latitude >>> 32 ^ $latitude);
        final long $longitude = Double.doubleToLongBits(this.getLongitude());
        result = result * PRIME + (int) ($longitude >>> 32 ^ $longitude);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Coordinates;
    }

    public String toString() {
        return "Coordinates(name=" + this.getName() + ", latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ")";
    }
}

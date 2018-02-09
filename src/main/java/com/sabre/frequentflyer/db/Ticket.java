package com.sabre.frequentflyer.db;

import java.util.Date;

public class Ticket {
    /**
     * id of airport user departure
     */
    private String fromId;
    /**
     * id of airport user arrive
     */
    private String toId;
    /**
     * id of carrier
     */
    private String carrierID;
    /**
     * unique flight id
     */
    private int flightId;
    /**
     * flight class {Economy,Premium Economy, Business, First}
     */
    private String fClass;
    /**
     * flight type {roundTrip,oneWay}
     */
    private String fType;
    /**
     * date of departure
     */
    private Date date;
    /**
     * flight distance
     */
    private int distance;

    public Ticket(String fromId, String toId, String carrierID, int flightId, String fClass, String fType, Date date) {
        this.fromId = fromId;
        this.toId = toId;
        this.carrierID = carrierID;
        this.flightId = flightId;
        this.fClass = fClass;
        this.fType = fType;
        this.date = date;
        this.distance = 0;
    }

    @java.beans.ConstructorProperties({"fromId", "toId", "carrierID", "flightId", "fClass", "fType", "date", "distance"})
    public Ticket(String fromId, String toId, String carrierID, int flightId, String fClass, String fType, Date date, int distance) {
        this.fromId = fromId;
        this.toId = toId;
        this.carrierID = carrierID;
        this.flightId = flightId;
        this.fClass = fClass;
        this.fType = fType;
        this.date = date;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", date=" + date +
                ", distance=" + distance +
                '}';
    }
    /**
     * -- GETTER --
     *  Returns <code>String</code> departure airport
     */
    public String getFromId() {
        return this.fromId;
    }
    /**
     * -- GETTER --
     * Returns <code>String</code> arrive airport
     */
    public String getToId() {
        return this.toId;
    }
    /**
     * -- GETTER --
     * Returns <code>String</code> carrier id
     */
    public String getCarrierID() {
        return this.carrierID;
    }
    /**
     * -- GETTER --
     * Returns <code>int</code> flight id
     */
    public int getFlightId() {
        return this.flightId;
    }
    /**
     * -- GETTER --
     * Returns <code>String</code> of flight class.
     */
    public String getFClass() {
        return this.fClass;
    }
    /**
     * -- GETTER --
     * Returns <code>String</code> of flight type.
     */
    public String getFType() {
        return this.fType;
    }
    /**
     * -- GETTER --
     * Returns <code>Date</code> of flight.
     */
    public Date getDate() {
        return this.date;
    }
    /**
     * -- GETTER --
     * Returns <code>int</code> distance of flight.
     */
    public int getDistance() {
        return this.distance;
    }
    /**
     * -- SETTER --
     * Sets the airport identifier.
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    /**
     * -- SETTER --
     * Sets the airport identifier.
     */
    public void setToId(String toId) {
        this.toId = toId;
    }
    /**
     * -- SETTER --
     * Sets the carrier identifier.
     */
    public void setCarrierID(String carrierID) {
        this.carrierID = carrierID;
    }
    /**
     * -- SETTER --
     * Sets the flight identifier.
     */
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
    /**
     * -- SETTER --
     * Sets the flight class.
     */
    public void setFClass(String fClass) {
        this.fClass = fClass;
    }
    /**
     * -- SETTER --
     * Sets the flight type.
     */
    public void setFType(String fType) {
        this.fType = fType;
    }
    /**
     * -- SETTER --
     * Sets the flight date.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * -- SETTER --
     * Sets the flight distance.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ticket)) return false;
        final Ticket other = (Ticket) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fromId = this.getFromId();
        final Object other$fromId = other.getFromId();
        if (this$fromId == null ? other$fromId != null : !this$fromId.equals(other$fromId)) return false;
        final Object this$toId = this.getToId();
        final Object other$toId = other.getToId();
        if (this$toId == null ? other$toId != null : !this$toId.equals(other$toId)) return false;
        final Object this$carrierID = this.getCarrierID();
        final Object other$carrierID = other.getCarrierID();
        if (this$carrierID == null ? other$carrierID != null : !this$carrierID.equals(other$carrierID)) return false;
        if (this.getFlightId() != other.getFlightId()) return false;
        final Object this$fClass = this.getFClass();
        final Object other$fClass = other.getFClass();
        if (this$fClass == null ? other$fClass != null : !this$fClass.equals(other$fClass)) return false;
        final Object this$fType = this.getFType();
        final Object other$fType = other.getFType();
        if (this$fType == null ? other$fType != null : !this$fType.equals(other$fType)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        if (this.getDistance() != other.getDistance()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fromId = this.getFromId();
        result = result * PRIME + ($fromId == null ? 43 : $fromId.hashCode());
        final Object $toId = this.getToId();
        result = result * PRIME + ($toId == null ? 43 : $toId.hashCode());
        final Object $carrierID = this.getCarrierID();
        result = result * PRIME + ($carrierID == null ? 43 : $carrierID.hashCode());
        result = result * PRIME + this.getFlightId();
        final Object $fClass = this.getFClass();
        result = result * PRIME + ($fClass == null ? 43 : $fClass.hashCode());
        final Object $fType = this.getFType();
        result = result * PRIME + ($fType == null ? 43 : $fType.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        result = result * PRIME + this.getDistance();
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Ticket;
    }
}

package com.sabre.frequentflyer.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Ticket {
    /**
     * id of airport user departure
     * -- GETTER --
     * Returns <code>String</code> airport
     * -- SETTER --
     * Sets the airport identifier.
     */
    private String fromId;
    /**
     * id of airport user arrive
     * -- GETTER --
     * Returns <code>String</code> airport
     * -- SETTER --
     * Sets the airport identifier.
     */
    private String toId;
    /**
     * id of carrier
     * -- GETTER --
     * Returns <code>String</code> carrier
     * -- SETTER --
     * Sets the carrier identifier.
     */
    private String carrierID;
    /**
     * unique flight id
     * -- GETTER --
     * Returns <code>int</code> flight id
     * -- SETTER --
     * Sets the flight identifier.
     */
    private int flightId;
    /**
     * flight class {Economy,Premium Economy, Business, First}
     * -- GETTER --
     * Returns <code>String</code> of flight class.
     * -- SETTER --
     * Sets the flight class.
     */
    private String fClass;
    /**
     * flight type {roundTrip,oneWay}
     * -- GETTER --
     * Returns <code>String</code> of flight type.
     * -- SETTER --
     * Sets the flight type.
     */
    private String fType;
    /**
     * date of departure
     * -- GETTER --
     * Returns <code>Date</code> of flight.
     * -- SETTER --
     * Sets the flight date.
     */
    private Date date;
    /**
     * flight distance
     * -- GETTER --
     * Returns <code>int</code> distance of flight.
     * -- SETTER --
     * Sets the flight distance.
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

    @Override
    public String toString() {
        return "Ticket{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", date=" + date +
                ", distance=" + distance +
                '}';
    }
}

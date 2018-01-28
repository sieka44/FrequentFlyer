package com.sabre.frequentflyer.db;

import java.util.Date;

public class Ticket {
    private String fromId;
    private String toId;
    private String carrierID;
    private int flightId;
    private String fClass;
    private String fType;
    private Date date;
    private int distance;


    public Ticket(String fromId, String toId, String carrierID, int flightId, String fClass, String fType, Date date) {
        this.fromId = fromId;
        this.toId = toId;
        this.carrierID = carrierID;
        this.flightId = flightId;
        this.fClass = fClass;
        this.fType = fType;
        this.date = date;
        distance = 0;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getCarrierID() {
        return carrierID;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getfClass() {
        return fClass;
    }

    public String getfType() {
        return fType;
    }

    public Date getDate() {
        return date;
    }

    public void setDistance(int distance) {
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
}

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
    private Double distance;


    public Ticket(String fromId, String toId, String carrierID, int flightId, String fClass, String fType, Date date) {
        this.fromId = fromId;
        this.toId = toId;
        this.carrierID = carrierID;
        this.flightId = flightId;
        this.fClass = fClass;
        this.fType = fType;
        this.date = date;
        distance = 0.0;
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

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}

/*
Noah; Williams; noah.williams@travel-sabre.com; LHR; EDI; BA; 1454; Business; oneWay; 2017-08-06;
Noah; Williams; noah.williams@travel-sabre.com; EDI; LHR; BA; 4541; Business; oneWay; 2017-08-09;

Ethan; White; ethan.white@travel-sabre.com; ORD; SAN; AS; 6972; Economy; oneWay; 2008-01-02;
Ethan; White; ethan.white@travel-sabre.com; SAN; ORD; AS; 2796; Economy; oneWay; 2008-01-05;
*/
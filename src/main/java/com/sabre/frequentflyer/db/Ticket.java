package com.sabre.frequentflyer.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
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

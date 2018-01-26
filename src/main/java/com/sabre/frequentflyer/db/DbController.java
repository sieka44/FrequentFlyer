package com.sabre.frequentflyer.db;

import org.apache.commons.collections.map.MultiValueMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DbController {
    MultiValueMap tickets;

    public DbController() {
        tickets = new MultiValueMap();
    }

    public void addTicket(String userMail, Ticket ticket) {
        tickets.put(userMail, ticket);
    }

    public void loadDbData(String fileName) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null && !(line.equals("//*//"))) {
                String[] data = line.split(";");
                if (data.length < 10) continue;
                String email = data[2].trim();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(data[9].trim());
                Ticket ticket = new Ticket(data[3].trim(), data[4].trim(),
                        data[5].trim(), Integer.parseInt(data[6].trim()),
                        data[7].trim(), data[8].trim(), date);
                tickets.put(email, ticket);
                if (!data[8].trim().equals("oneWay")) {
                    double distance = Double.parseDouble(data[11].trim());
                    ticket.setDistance(distance);
                    //TODO: Update miles
                    ticket = new Ticket(data[3].trim(), data[4].trim(),
                            data[5].trim(), Integer.parseInt(data[6].trim()), data[7].trim(), data[8].trim(), dateFormat.parse(data[10].trim()));
                    distance = Double.parseDouble(data[11].trim());
                    ticket.setDistance(distance);
                    //TODO: Update miles
                    tickets.put(data[2].trim(), ticket);
                } else ticket.setDistance(Double.parseDouble(data[10].trim()));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List getTickets(String email) {
        return new LinkedList(tickets.getCollection(email));
    }
}

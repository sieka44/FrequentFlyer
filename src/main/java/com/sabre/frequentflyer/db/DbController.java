package com.sabre.frequentflyer.db;

import org.apache.commons.collections.map.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class DbController {
    MultiValueMap tickets;

    /**
     * Constructor which creates new <code>MultiValueMap</code> and load tickets from file
     *
     * @param filename csv file with mock tickets data
     */
    public DbController(String filename) {
        tickets = new MultiValueMap();
        loadDbData(filename);
    }

    /**
     * Adds ticket to map with key user email and value as <code>new Ticket</code>
     *
     * @param userMail email of logged in user
     * @param ticket   ticket which we want to add to database
     */
    public void addTicket(String userMail, Ticket ticket) {
        tickets.put(userMail, ticket);
    }

    /**
     * Loads data from .csv file
     *
     * @param fileName path to file with data
     */
    private void loadDbData(String fileName) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/" + fileName)));
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
                    int distance = Integer.parseInt(data[11].trim());
                    ticket.setDistance(distance);
                    ticket = new Ticket(data[4].trim(), data[3].trim(),
                            data[5].trim(), Integer.parseInt(data[6].trim()), data[7].trim(),
                            data[8].trim(), dateFormat.parse(data[10].trim()));
                    distance = Integer.parseInt(data[11].trim());
                    ticket.setDistance(distance);
                    tickets.put(data[2].trim(), ticket);
                } else ticket.setDistance(Integer.parseInt(data[10].trim()));
                System.out.println(ticket);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns tickets of given user email
     *
     * @param email user email
     * @return tickets of given user
     */
    public Collection getTickets(String email) {
        return tickets.getCollection(email);
    }
}

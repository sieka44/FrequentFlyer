package com.sabre.frequentflyer.db;


import org.apache.commons.collections4.map.MultiValueMap;

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
    LinkedList<User> users;

    public DbController(){
        tickets = new MultiValueMap();
        users = new LinkedList<>();

    }

    public boolean isRegistered(String userMail){
        return users.contains(userMail);
    }

    public void addTicket(String userMail, Ticket ticket){
        tickets.put(userMail,ticket);
    }

    public void loadDbData(String fileName){
        BufferedReader br = null;
        String line;
        try{
            br= new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null){
                String[] data = line.split(";");
                if(data.length < 10) continue;
                users.add(new User(data[0].trim(),data[1].trim(),data[2].trim()));

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date tmpDate = format.parse(data[9].trim());

                tickets.put(data[2].trim(),new Ticket(data[3].trim(),data[4].trim(),
                        data[5].trim(), Integer.parseInt(data[6]),data[7].trim(),data[8].trim(),tmpDate));
                if(!data[8].equalsIgnoreCase("oneway")){
                    tickets.put(data[2].trim(),new Ticket(data[3].trim(),data[4].trim(),
                            data[5].trim(), Integer.parseInt(data[6]),data[7].trim(),data[8].trim(),format.parse(data[10].trim())));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List getTickets(String email){
        return new LinkedList(tickets.getCollection(email));
    }

}

/*
Noah; Williams; noah.williams@travel-sabre.com; LHR; EDI; BA; 1454; Business; oneWay; 2017-08-06;
Noah; Williams; noah.williams@travel-sabre.com; EDI; LHR; BA; 4541; Business; oneWay; 2017-08-09;

Ethan; White; ethan.white@travel-sabre.com; ORD; SAN; AS; 6972; Economy; oneWay; 2008-01-02;
Ethan; White; ethan.white@travel-sabre.com; SAN; ORD; AS; 2796; Economy; oneWay; 2008-01-05;
*/

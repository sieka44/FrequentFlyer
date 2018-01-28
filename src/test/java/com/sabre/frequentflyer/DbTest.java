package com.sabre.frequentflyer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sabre.frequentflyer.db.DbController;
import com.sabre.frequentflyer.db.Ticket;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DbTest {
    DbController database;

    @Before
    public void init() {
        database = new DbController();
        database.loadDbData("Test.csv");
    }

    @Test
    public void toStringTest() {
        List list = database.getTickets("ethan.white@travel-sabre.com");
        System.out.println(list);
    }

    @Test
    public void addTicket() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2016-09-14");
        Ticket ticket = new Ticket("SAN", "LAX", "AA", 11112, "Business", "oneWay", date);
        database.addTicket("ethan.white@travel-sabre.com", ticket);
        List list = database.getTickets("ethan.white@travel-sabre.com");
        System.out.println(list);
    }

    @Test
    public void getrefreshToken() throws JSONException, UnirestException {
        HttpResponse<String> response = Unirest.post("https://frequent-flyer.eu.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"9FI4KE9hAbqGlvlZCt6uGDM7leYaJ0Ro\",\"client_secret\":\"UYYeSicRcqFmdWI3fe9c5hhM6JL00lCeb6rNtHdZY8t500h-SAgUA0hHLD6MF5lD\",\"audience\":\"https://frequent-flyer.eu.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                .asString();
        Unirest.setTimeouts(0, 0);
        JSONObject json = new JSONObject(response.getBody());
        System.out.println(json.get("access_token").toString());
    }
}

package com.sabre.frequentflyer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sabre.frequentflyer.controllers.ManagementApiController;
import com.sabre.frequentflyer.db.DbController;
import com.sabre.frequentflyer.db.Ticket;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DbTest {
    DbController database;

    @Before
    public void init() {
        database = new DbController("Test.csv");
    }

    @Test
    public void toStringTest() {
        List list = new LinkedList(database.getTickets("ethan.white@travel-sabre.com"));
        System.out.println(list);
    }

    @Test
    public void addTicket() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd", Locale.US).parse("2016-09-14");
        Ticket ticket = new Ticket("SAN", "LAX", "AA", 11112, "Business", "oneWay", date);
        database.addTicket("ethan.white@travel-sabre.com", ticket);
        List list = Arrays.asList(database.getTickets("ethan.white@travel-sabre.com"));
        System.out.println(list);
    }

    @Test
    public void getRefreshToken() throws JSONException, UnirestException {
        HttpResponse<String> response = Unirest.post("https://frequent-flyer.eu.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"9FI4KE9hAbqGlvlZCt6uGDM7leYaJ0Ro\",\"client_secret\":\"UYYeSicRcqFmdWI3fe9c5hhM6JL00lCeb6rNtHdZY8t500h-SAgUA0hHLD6MF5lD\",\"audience\":\"https://frequent-flyer.eu.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                .asString();
        Unirest.setTimeouts(0, 0);
        JSONObject json = new JSONObject(response.getBody());
        Assert.assertNotNull(json.get("access_token"));
    }
}

package com.sabre.frequentflyer;

import com.sabre.frequentflyer.db.DbController;
import com.sabre.frequentflyer.db.Ticket;
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
        database.addTicket("ethan.white@travel-sabre.com", new Ticket("SAN", "LAX", "AA", 11112, "Business", "oneWay", date));
        List list = database.getTickets("ethan.white@travel-sabre.com");
        System.out.println(list);
    }
}

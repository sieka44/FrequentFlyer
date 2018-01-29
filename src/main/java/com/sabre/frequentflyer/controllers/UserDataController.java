package com.sabre.frequentflyer.controllers;

import com.sabre.frequentflyer.api.APIController;
import com.sabre.frequentflyer.db.DbController;
import com.sabre.frequentflyer.db.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

@Controller
public class UserDataController {
    DbController database = new DbController("Test.csv");

    /**
     * Checks user status based on miles he traveled
     * @param userId  id of authored user logged in by Auth0
     * @return <code>ResponseEntity</code> with <code>String</code> of the current status
     */
    @RequestMapping(value = "/api/v1/getUserStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUserStatus(@RequestParam String userId) {
        int miles = ManagementApiController.getUserMiles(userId);
        return ResponseEntity.ok().body(ManagementApiController.checkStatus(miles).getClass());
    }

    /**
     * Connects with Auth0 API and updates user <code>name</code> and <code>address</code> store in user metadata
     *
     * @param userId  id of authored user logged in by Auth0
     * @param name    new name to update
     * @param address new address to update
     * @return response from Auth0 API
     */
    @RequestMapping(value = "/api/v1/updateProfile", method = RequestMethod.POST)
    @ResponseBody
    public String updateProfile(@RequestParam String userId,
                                @RequestParam String name,
                                @RequestParam String address) {
        return ManagementApiController.updateUser(userId, name, address);
    }

    /**
     * Adds ticket to database introduced by user
     *
     * @param userId        id of authored user logged in by Auth0
     * @param email         user email
     * @param fromId        id of airport user departure
     * @param toId          id of airport user arrive
     * @param carrierId     id of carrier
     * @param flightId      unique flight id
     * @param fClass        flight class {Economy,Premium Economy, Business, First}
     * @param fType         flight type {roundTrip,oneWay}
     * @param departureDate date of departure
     * @param returnDate    if flight type is <code>roundTrip</code>, it is date of return flight
     * @return <code>String</code> with updated user metadata
     * @throws ParseException if <code>DataFormat</code> is not supported
     */
    @RequestMapping(value = "/api/v1/addTicket", method = RequestMethod.POST)
    @ResponseBody
    public String addTicket(@RequestParam String userId,
                            @RequestParam String email,
                            @RequestParam String fromId,
                            @RequestParam String toId,
                            @RequestParam String carrierId,//id przewoźnika
                            @RequestParam int flightId,
                            @RequestParam String fClass,//klasa przelotu {Economy,Premium Economy, Business, First}
                            @RequestParam String fType,//rodzaj biletu {roundTrip,oneWay}
                            @RequestParam String departureDate,
                            @RequestParam String returnDate) throws ParseException {//data, jak jest roundTrip to datax2 wylotu i powrot
        DateFormat df = new SimpleDateFormat("d MMMM, yyyy", Locale.US);
        Date dDate = df.parse(departureDate);
        Ticket ticket = new Ticket(fromId, toId, carrierId, flightId, fClass, fType, dDate);
        int distance = APIController.getDistance(fromId, toId);
        ticket.setDistance(distance);
        database.addTicket(email, ticket);
        if (fType.equals("roundTrip")) {
            Date rDate = df.parse(returnDate);
            Ticket returnTicket = new Ticket(fromId, toId, carrierId, flightId, fClass, fType, rDate);
            returnTicket.setDistance(distance);
            database.addTicket(email, returnTicket);
            distance += distance;
        }
        return ManagementApiController.updatePoints(userId, distance);
    }

    /**
     * Gets all the tickets for specified user
     * @param email email of logged in user
     * @return <code>Collection</code> of the <code>Ticket</code>s
     */
    @RequestMapping(value = "/api/v1/getTickets", method = RequestMethod.GET)
    @ResponseBody
    public Collection getTickets(@RequestParam String email) {
        return database.getTickets(email);
    }
}

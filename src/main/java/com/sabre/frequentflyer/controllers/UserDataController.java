package com.sabre.frequentflyer.controllers;

import com.sabre.frequentflyer.api.APIController;
import com.sabre.frequentflyer.db.DbController;
import com.sabre.frequentflyer.db.Ticket;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

@Controller
public class UserDataController {
    DbController database = new DbController();

    /**
     * Checks user status based on miles he traveled
     *
     * @return <code>ResponsEntity</code> with <code>String</code> of the current status
     */
    @RequestMapping(value = "/api/v1/getUserStatus", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> getUserStatus(@RequestParam String userID) {
        int miles = ManagementApiController.getUserMiles(userID);
        return ResponseEntity.ok().body(ManagementApiController.checkStatus(miles));
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
     * @param fromId        id of airport he departure
     * @param toId          id of airport he arrive
     * @param carrierID     id of carrier
     * @param flightId      unique flight id
     * @param fClass        flight class {Economy,Premium Economy, Business, First}
     * @param fType         flight type {roundTrip,oneWay}
     * @param departureDate date of departure
     * @param returnDate    if flight type is <code>roundTrip</code>, it is date of return flight
     * @return <code>RedirectView</code> on the main page
     */
    @RequestMapping(value = "/api/v1/addTicket", method = RequestMethod.POST)
    public RedirectView addTicket(@RequestParam String userId,
                                  @RequestParam String email,
                                  @RequestParam String fromId,
                                  @RequestParam String toId,
                                  @RequestParam String carrierID,//id przewoźnika
                                  @RequestParam int flightId,
                                  @RequestParam String fClass,//klasa przelotu {Economy,Premium Economy, Business, First}
                                  @RequestParam String fType,//rodzaj biletu {roundTrip,oneWay}
                                  @RequestParam Date departureDate,
                                  @RequestParam Date returnDate) {//data, jak jest roundTrip to datax2 wylotu i powrot
        Ticket ticket = new Ticket(fromId, toId, carrierID, flightId, fClass, fType, departureDate);
        int distance = APIController.getDistance(fromId, toId);
        ticket.setDistance(distance);
        database.addTicket(email, ticket);
        if (fType.equals("roundTrip")) {
            Ticket returnTicket = new Ticket(fromId, toId, carrierID, flightId, fClass, fType, returnDate);
            returnTicket.setDistance(distance);
            database.addTicket(email, returnTicket);
            distance += distance;
        }
        ManagementApiController.updatePoints(userId, distance);
        return new RedirectView("/");
    }

    /**
     * Gets all the tickets for specify user
     * @param email email of logged in user
     * @return <code>JSONObject</code> of the <code>List</code> with all tickets
     */
    @RequestMapping(value = "/api/v1/getTickets", method = RequestMethod.POST)
    public JSONObject getTickets(@RequestParam String email) {
        return new JSONObject(database.getTickets(email));
    }

}

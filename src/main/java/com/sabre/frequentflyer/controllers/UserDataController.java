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

    @RequestMapping(value = "/api/v1/getUserStatus", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> getUserStatus() {
        return ResponseEntity.ok().body("Hello from controller!");
    }

    @RequestMapping(value = "/api/v1/updateProfile", method = RequestMethod.POST)
    @ResponseBody
    public String updateProfile(@RequestParam String userId,
                                @RequestParam String name,
                                @RequestParam String address) {
        System.out.println(address);
        return ManagementApiController.updateUser(userId, name, address);
    }

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

    @RequestMapping(value = "/api/v1/getTickets", method = RequestMethod.POST)
    public JSONObject getTickets(@RequestParam String email) {
        return new JSONObject(database.getTickets(email));
    }

}

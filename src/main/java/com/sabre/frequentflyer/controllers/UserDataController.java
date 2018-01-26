package com.sabre.frequentflyer.controllers;

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
    public RedirectView addTicket(@RequestParam String fromId,
                                  @RequestParam String toId,
                                  @RequestParam String carrierID,//id przewoźnika
                                  @RequestParam int flightId,
                                  @RequestParam String fClass,//klasa przelotu {Economy,Premium Economy, Business, First}
                                  @RequestParam String fType,//rodzaj biletu {roundTrip,oneWay}
                                  @RequestParam Date date) {//data, jak jest roundTrip to datax2 wylotu i powrot

        return new RedirectView("/");
    }

    @RequestMapping(value = "/api/v1/getTickets", method = RequestMethod.POST)
    public RedirectView getTickets(@RequestParam String email) {

        return new RedirectView("/");
    }

}

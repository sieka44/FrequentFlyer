package com.sabre.frequentflyer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserDataController {
    @RequestMapping(value = "/api/v1/getUserStatus", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> getUserStatus() {
        return ResponseEntity.ok().body("Hello from controller!");
    }

    @RequestMapping(value = "/api/v1/updateProfile", method = RequestMethod.POST)
    public RedirectView updateProfile(@RequestParam String name,
                                      @RequestParam String address,
                                      @RequestParam String token) {
        //Here update the profile
        return new RedirectView("/");
    }
}

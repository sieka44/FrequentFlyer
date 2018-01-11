package com.sabre.frequentflyer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserDataController {
    @RequestMapping(value = "/api/v1/getUserStatus", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> request() {
        return ResponseEntity.ok().body("Hello from controller!");
    }
}

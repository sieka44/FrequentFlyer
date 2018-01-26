package com.sabre.frequentflyer.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ManagementApiController {
    private static String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFUSXlPRVl6UXpCQk9VVTJRakpHUlVNNFFqWTROelZFT0ROQ1JEZEZSRUpDTXpZeVJVRXlPQSJ9";

    public static String updateUser(String userID, String name, String address) {
        try {
            HttpResponse<String> response = Unirest.patch("https://frequent-flyer.eu.auth0.com/api/v2/users/" + userID)
                    .header("authorization", "Bearer " + accessToken)
                    .header("content-type", "application/json")
                    .body("{\"user_metadata\":" +
                            " {\"name\": \"" + name + "\"" +
                            "\"address\": \"" + address + "\"" +
                            "}" +
                            "}")
                    .asString();
            Unirest.setTimeouts(0, 0);
//            System.out.println(response.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }


}

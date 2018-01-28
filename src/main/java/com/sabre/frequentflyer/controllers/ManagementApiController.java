package com.sabre.frequentflyer.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ManagementApiController {
    private static String accessToken = null;

    private static void refreshToken() {
        try {
            HttpResponse<String> response = Unirest.post("https://frequent-flyer.eu.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body("{\"client_id\":\"9FI4KE9hAbqGlvlZCt6uGDM7leYaJ0Ro\",\"client_secret\":\"UYYeSicRcqFmdWI3fe9c5hhM6JL00lCeb6rNtHdZY8t500h-SAgUA0hHLD6MF5lD\",\"audience\":\"https://frequent-flyer.eu.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                    .asString();
            Unirest.setTimeouts(0, 0);
            JSONObject json = new JSONObject(response.getBody());
            accessToken = json.get("access_token").toString();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static String updateUser(String userID, String name, String address) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {


            String request = "{ \"user_metadata\": " +
                    "{ \"address\": \"" + address + "\"," +
                    " \"name\": \"" + name + "\" }" +
                    " }";
            response = Unirest.patch("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userID, "UTF-8"))
                    .header("authorization", "Bearer " + accessToken)
                    .header("content-type", "application/json")
                    .body(request)
                    .asString();
            Unirest.setTimeouts(0, 0);
            byte[] b = response.getBody().getBytes();
            return new String(b, "UTF-8");
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getUserMiles(String userID) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {
            response = Unirest.get("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userID, "UTF-8") + "?fields=user_metadata")
                    .header("authorization", "Bearer " + accessToken)
                    .asString();
            Unirest.setTimeouts(0, 0);
            JSONObject json = new JSONObject(response.getBody());
            System.out.println(response.getBody());
            json = new JSONObject(json.get("user_metadata").toString());
            return Integer.parseInt(json.get("miles").toString());
        } catch (UnirestException | UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String updatePoints(String userID, int miles) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {
            miles += getUserMiles(userID);
            String request = "{ \"user_metadata\" : { \"miles\": " + miles + " } }";
            response = Unirest.patch("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userID, "UTF-8"))
                    .header("authorization", "Bearer " + accessToken)
                    .header("content-type", "application/json")
                    .body(request)
                    .asString();
            Unirest.setTimeouts(0, 0);
            return response.getBody();
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}

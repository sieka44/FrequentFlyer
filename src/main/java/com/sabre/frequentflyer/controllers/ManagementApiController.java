package com.sabre.frequentflyer.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ManagementApiController {
    private static String accessToken = null;

    /**
     * Refreshes access token to management Auth0 API which could expire during the time of program working.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2/tokens">How to get token</a>
     */
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

    /**
     * Updates Auth0 user metadata with <code>name</code> and <code>address</code> fields.
     *
     * @param userId  id of authored user logged in by Auth0
     * @param name    user name which will be saved in user metadata
     * @param address user address which will be saved in user metadata
     * @return <code>String</code> with response body.
     */
    public static String updateUser(String userId, String name, String address) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {
            String request = "{ \"user_metadata\": " +
                    "{ \"address\": \"" + address + "\"," +
                    " \"name\": \"" + name + "\" }" +
                    " }";
            response = Unirest.patch("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userId, "UTF-8"))
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

    /**
     * Connects with Auth0 Api to get user miles
     *
     * @param userId id of authored user logged in by Auth0
     * @return 0 if exception has been thrown, otherwise miles of given user
     */
    public static int getUserMiles(String userId) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {
            response = Unirest.get("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userId, "UTF-8") + "?fields=user_metadata")
                    .header("authorization", "Bearer " + accessToken)
                    .asString();
            Unirest.setTimeouts(0, 0);
            JSONObject json = new JSONObject(response.getBody());
            json = new JSONObject(json.get("user_metadata").toString());
            return Integer.parseInt(json.get("miles").toString());
        } catch (UnirestException | UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Connects with Auth0 APi to update user miles
     *
     * @param userId id of authored user logged in by Auth0
     * @param miles  amount of miles, which we want to add to the user pot
     * @return Body of response from the API as <code>String</code>, empty if update failure
     */
    public static String updatePoints(String userId, int miles) {
        if (accessToken == null) refreshToken();
        HttpResponse<String> response;
        try {
            miles += getUserMiles(userId);
            String request = "{ \"user_metadata\" : { \"miles\": " + miles + " } }";
            response = Unirest.patch("https://frequent-flyer.eu.auth0.com/api/v2/users/"
                    + URLEncoder.encode(userId, "UTF-8"))
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

    /**
     * Checks what is user status
     *
     * @param miles User miles which he currently own
     * @return current status as <code>String</code> with format (status;nextStatus;pointsToNext)
     */
    public static Response checkStatus(int miles) {
        if (miles < 1000) return new Response("notFF","bronze",1000-miles, miles/10);
        else if (miles < 4000) return new Response("bronze","silver",(4000-miles), miles/40);
        else if (miles < 8000) return new Response("silver","golden",(8000-miles), miles/80);
        else if (miles < 15000) return new Response("golden","platinum",(15000-miles), miles/150);
        else return new Response("platinum","",0, 100);
    }

    @Data
    @AllArgsConstructor
    static class Response {
        String status;
        String nextStatus;
        int howManyLeft;
        double progress;
    }
}

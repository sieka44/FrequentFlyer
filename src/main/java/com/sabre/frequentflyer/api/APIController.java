package com.sabre.frequentflyer.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class APIController {

    FFAPIConfig APIConfig= new FFAPIConfig();

    private static final String ACCESS_TOKEN_URL= "https://api-crt.cert.havail.sabre.com/v2/auth/token";
    private static final String GET_COORDINATES_URL= "https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations/";
    AccessToken accessToken = null;

    public AccessToken getToken() {
        return accessToken;
    }

    private String createTokenCredentials(){
        final byte[] domainBytes = APIConfig.getDomain().getBytes(StandardCharsets.UTF_8);
        final byte[] secretBytes = APIConfig.getClientSecret().getBytes(StandardCharsets.UTF_8);

        String outcome = Base64.getEncoder().encodeToString(domainBytes) + ":" +
                            Base64.getEncoder().encodeToString(secretBytes);
        return Base64.getEncoder().encodeToString(outcome.getBytes());
    }

    public boolean getAccessToken() {
        String credentials = createTokenCredentials();
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(ACCESS_TOKEN_URL)
                    .header("authorization", "Basic " + credentials)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("grant_type", "client_credentials")
                    .header("cache-control", "no-cache").asString();
            ObjectMapper mapper = new ObjectMapper();
            accessToken = mapper.readValue(response.getBody(),AccessToken.class);
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Coordinates[] getCoordinates(String city1,String city2) {
        if(accessToken==null)getAccessToken();

        HttpResponse<String> response = null;
        try {
            response = Unirest.post(GET_COORDINATES_URL)
                    .header("authorization", "Bearer "+accessToken.getAccess_token())
                    .header("content-type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("["+new GeoCodeRQ(city1).toString()+",\n"+new GeoCodeRQ(city2).toString()+"]")
                    .asString();
            JSONObject json = new JSONObject(response.getBody());
            JSONArray posts = (JSONArray) json.get("Results");
            Coordinates[] coordinates = new Coordinates[2];
            for (int i = 0; i<posts.length();i++) {
                JSONArray arr = posts.getJSONObject(i).getJSONObject("GeoCodeRS").getJSONArray("Place");
                System.out.println(arr.toString());
                String name = arr.getJSONObject(0).getString("City");
                double x = arr.getJSONObject(0).getDouble("latitude");
                double y = arr.getJSONObject(0).getDouble("longitude");
                coordinates[i]=new Coordinates(name,x,y);
            }
            return coordinates;
        } catch (UnirestException e) {
            e.printStackTrace();
            if(getAccessToken())return getCoordinates(city1,city2);
        }

        return null;
    }

    public double getDistance(Coordinates[] coordinates){
        return new HaversineFormula().distance(coordinates[0].getLatitude(),coordinates[0].getLongitude(),coordinates[1].getLatitude(),coordinates[1].getLongitude());
    }

}

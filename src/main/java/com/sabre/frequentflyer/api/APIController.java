package com.sabre.frequentflyer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class APIController {
    private static final String ACCESS_TOKEN_URL = "https://api-crt.cert.havail.sabre.com/v2/auth/token";
    private static final String GET_COORDINATES_URL
            = "https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations/";
    public final static String DOMAIN = "V1:56tl4jc7qge5xhb7:DEVCENTER:EXT";
    public final static String CLIENT_SECRET = "GPmcw02H";

    private static AccessToken accessToken;

    private APIController() {
        //not for instantiation
    }

    public static final AccessToken getAccessToken() {
        return accessToken;
    }

    private static String createTokenCredentials() {
        final byte[] domainBytes = DOMAIN.getBytes(StandardCharsets.UTF_8);
        final byte[] secretBytes = CLIENT_SECRET.getBytes(StandardCharsets.UTF_8);

        String outcome = Base64.getEncoder().encodeToString(domainBytes) + ":" +
                Base64.getEncoder().encodeToString(secretBytes);
        return Base64.getEncoder().encodeToString(outcome.getBytes());
    }

    private static void refreshToken() {
        String credentials = createTokenCredentials();
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(ACCESS_TOKEN_URL)
                    .header("authorization", "Basic " + credentials)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("grant_type", "client_credentials")
                    .header("cache-control", "no-cache").asString();
            ObjectMapper mapper = new ObjectMapper();
            accessToken = mapper.readValue(response.getBody(), AccessToken.class);
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
    }

    static {
        refreshToken();
    }

    public static int getDistance(String city1, String city2) {
        if (accessToken == null) {
            refreshToken();
        }
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(GET_COORDINATES_URL)
                    .header("authorization", "Bearer " + accessToken.getAccess_token())
                    .header("content-type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("[" + new GeoCodeRQ(city1).toString() + ",\n" + new GeoCodeRQ(city2).toString() + "]")
                    .asString();
            Unirest.setTimeouts(0, 0);
            JSONObject json = new JSONObject(response.getBody());
            JSONArray posts = (JSONArray) json.get("Results");
            Coordinates[] coordinates = new Coordinates[2];
            for (int i = 0; i < posts.length(); i++) {
                JSONArray arr = posts.getJSONObject(i).getJSONObject("GeoCodeRS").getJSONArray("Place");
                System.out.println(arr.toString());
                String name = arr.getJSONObject(0).getString("City");
                double x = arr.getJSONObject(0).getDouble("latitude");
                double y = arr.getJSONObject(0).getDouble("longitude");
                coordinates[i] = new Coordinates(name, x, y);
            }
            return getMiles(coordinates);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            accessToken = null;
        }
        return 0;
    }

    public static int getMiles(Coordinates[] coordinates) {
        return HaversineFormula.distance(
                coordinates[0].getLatitude(), coordinates[0].getLongitude(),
                coordinates[1].getLatitude(), coordinates[1].getLongitude()
        );
    }
}

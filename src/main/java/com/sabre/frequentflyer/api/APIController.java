package com.sabre.frequentflyer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
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
    private static final String DOMAIN = "V1:56tl4jc7qge5xhb7:DEVCENTER:EXT";
    private static final String CLIENT_SECRET = "GPmcw02H";

    /**
     * -- GETTER --
     * Returns <code>String</code> object that can be used to connect with
     * GeoCode API.
     *
     * @return returns String object with the access token
     */
    @Getter
    private static AccessToken accessToken;

    /**
     * This is a utility class, not designed for instantiation.
     */
    private APIController() { }

    /**
     * Returns <code>String</code> object with encoded client secret and
     * domain that can be used to obtain access token.
     *
     * @return encoded client credentials
     */
    private static String createTokenCredentials() {
        final byte[] domainBytes = DOMAIN.getBytes(StandardCharsets.UTF_8);
        final byte[] secretBytes = CLIENT_SECRET.getBytes(StandardCharsets.UTF_8);

        String outcome = Base64.getEncoder().encodeToString(domainBytes) + ":" +
                Base64.getEncoder().encodeToString(secretBytes);
        return Base64.getEncoder().encodeToString(outcome.getBytes());
    }

    /**
     * Refreshes access token which could expire during the time of program working.
     *
     * @see <a href="https://developer.sabre.com/page/read/resources/getting_started_with_sabre_apis/how_to_get_a_token">How to get token</a>
     */
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

    /**
     * Refreshes token when application is started.
     */
    static {
        refreshToken();
    }

    /**
     * Connects with GeoCode API which provides us latitude and longitude of airport1 and airport2 to
     * calculate distance between them.
     *
     * @param airport1 three-letter identifier of first airport
     * @param airport2 three-letter identifier of second airport
     * @return 0 if any of <code>Exception</code> occurred, otherwise returns distance between <code>airport1</code>
     * and <code>airport2</code>.
     * @throws UnirestException if unirest cannot connect with given API url.
     * @throws JSONException    if response mismatch with expected result, then refresh access token.
     * @see <a href="https://developer.sabre.com/docs/read/rest_apis/utility/geo_code/">How to use GeoCode</a>
     */
    public static int getDistance(String airport1, String airport2) {
        if (accessToken == null) {
            refreshToken();
        }
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(GET_COORDINATES_URL)
                    .header("authorization", "Bearer " + accessToken.getAccessToken())
                    .header("content-type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("[" + new GeoCodeRQ(airport1).toString() + ",\n" + new GeoCodeRQ(airport2).toString() + "]")
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

    /**
     * Calculate distance between given two points (latitude, longitude) using haversine formula.
     *
     * @param coordinates array of two coordinates.
     * @return distance between two points in meters.
     * @see HaversineFormula
     */
    public static int getMiles(Coordinates[] coordinates) {
        return HaversineFormula.distance(
                coordinates[0].getLatitude(), coordinates[0].getLongitude(),
                coordinates[1].getLatitude(), coordinates[1].getLongitude()
        );
    }
}

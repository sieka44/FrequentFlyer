package com.sabre.frequentflyer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sabre.frequentflyer.api.APIController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class APITest extends FrequentFlyerApplication {

    private final static String URL = "https://api-crt.cert.havail.sabre.com/v2/auth/token";

    @Test
    public void getAccessTokenTest() throws UnirestException, IOException {
        APIController apiController = new APIController();
        apiController.getAccesToken();
        System.out.println(apiController.getToken());
    }
    @Test
    public void getCoordinatesTest() throws IOException, UnirestException {
        APIController controller = new APIController();
        controller.getCoordinates("KRK","ATL");
    }
    @Test
    public void JSONTest(){
        String a = "{\"Results\":[{\"GeoCodeRS\":{\"Place\":[{\"Category\":\"AIR\",\"latitude\":50.078056,\"Country\":\"PL\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"KRK\",\"City\":\"Krakow\",\"longitude\":19.786111,\"Name\":\"Krakow\"}],\"status\":\"ONE_PLACE_FOUND\"}},{\"GeoCodeRS\":{\"Place\":[{\"Category\":\"AIR\",\"State\":\"GA\",\"latitude\":33.640278,\"Country\":\"US\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"ATL\",\"City\":\"Atlanta\",\"longitude\":-84.426944,\"Name\":\"Atlanta\"}],\"status\":\"ONE_PLACE_FOUND\"}}],\"Links\":[{\"rel\":\"self\",\"href\":\"https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations/\"},{\"rel\":\"linkTemplate\",\"href\":\"https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations\"}]}";

        JSONObject json = new JSONObject(a);
        JSONArray posts = (JSONArray) json.get("Results");
        for (int i = 0; i<posts.length();i++) {
            JSONArray arr = posts.getJSONObject(i).getJSONObject("GeoCodeRS").getJSONArray("Place");
            System.out.println(arr.toString());
            double latitude = arr.getJSONObject(0).getDouble("latitude");
            double longitude = arr.getJSONObject(0).getDouble("longitude");
            System.out.println(latitude + " " + longitude);
        }
    }

}

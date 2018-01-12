package com.sabre.frequentflyer;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sabre.frequentflyer.api.APIController;
import com.sabre.frequentflyer.api.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class APITest {

    private final static String URL = "https://api-crt.cert.havail.sabre.com/v2/auth/token";

    @Test
    public void getAccessTokenTest(){
        APIController apiController = new APIController();
        apiController.getAccessToken();
        System.out.println(apiController.getToken());
    }
    @Test
    public void getCoordinatesTest() {
        APIController controller = new APIController();
        controller.getCoordinates("KRK","ATL");
    }
    @Test
    public void parsingJSONTest(){
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

    @Test
    public void getDistanceTest() throws IOException, UnirestException {
        APIController controller = new APIController();
        Coordinates[] coordinates = controller.getCoordinates("KRK","ATL");
        System.out.println(controller.getDistance(coordinates)+ " km");
        System.out.println((double) (8110-2)<controller.getDistance(coordinates)&&controller.getDistance(coordinates)<(double)(8110+2));
    }

}

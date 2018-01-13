package com.sabre.frequentflyer;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sabre.frequentflyer.api.APIController;
import com.sabre.frequentflyer.api.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
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
    public void getAccessTokenTest() {
        Assert.assertNotNull(APIController.getAccessToken());
    }

    @Test
    public void getCoordinatesTest() {
        Assert.assertNotNull(APIController.getCoordinates("KRK", "ATL"));
    }

    @Test
    public void parsingJSONTest() {
        final String jsonString = "{\"Results\":[{\"GeoCodeRS\":{\"Place\":[{\"Category\":\"AIR\",\"latitude\":50.078056,\"Country\":\"PL\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"KRK\",\"City\":\"Krakow\",\"longitude\":19.786111,\"Name\":\"Krakow\"}],\"status\":\"ONE_PLACE_FOUND\"}},{\"GeoCodeRS\":{\"Place\":[{\"Category\":\"AIR\",\"State\":\"GA\",\"latitude\":33.640278,\"Country\":\"US\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"ATL\",\"City\":\"Atlanta\",\"longitude\":-84.426944,\"Name\":\"Atlanta\"}],\"status\":\"ONE_PLACE_FOUND\"}}],\"Links\":[{\"rel\":\"self\",\"href\":\"https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations/\"},{\"rel\":\"linkTemplate\",\"href\":\"https://api-crt.cert.havail.sabre.com/v1/lists/utilities/geocode/locations\"}]}";
        final String fragment1 = "[{\"Category\":\"AIR\",\"latitude\":50.078056,\"Country\":\"PL\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"KRK\",\"City\":\"Krakow\",\"longitude\":19.786111,\"Name\":\"Krakow\"}]";
        final String fragment2 = "[{\"Category\":\"AIR\",\"State\":\"GA\",\"latitude\":33.640278,\"Country\":\"US\",\"confidenceFactor\":\"ADDRESS_QUALITY\",\"Id\":\"ATL\",\"City\":\"Atlanta\",\"longitude\":-84.426944,\"Name\":\"Atlanta\"}]";

        JSONObject json = new JSONObject(jsonString);
        JSONArray posts = (JSONArray) json.get("Results");

        // test first fragment

        JSONArray arr = posts.getJSONObject(0).getJSONObject("GeoCodeRS").getJSONArray("Place");
        Assert.assertEquals(fragment1, arr.toString());

        double latitude = arr.getJSONObject(0).getDouble("latitude");
        double longitude = arr.getJSONObject(0).getDouble("longitude");

        Assert.assertEquals(50.078056, latitude, 0.0001);
        Assert.assertEquals(19.786111, longitude, 0.0001);

        // test second fragment

        arr = posts.getJSONObject(1).getJSONObject("GeoCodeRS").getJSONArray("Place");
        Assert.assertEquals(fragment2, arr.toString());

        latitude = arr.getJSONObject(0).getDouble("latitude");
        longitude = arr.getJSONObject(0).getDouble("longitude");

        Assert.assertEquals(33.640278, latitude, 0.0001);
        Assert.assertEquals(-84.426944, longitude, 0.0001);
    }

    @Test
    public void getDistanceTest() throws IOException, UnirestException {
        Coordinates[] coordinates = APIController.getCoordinates("KRK", "ATL");
        System.out.println(APIController.getDistance(coordinates) + " km");
        System.out.println((double) (8110 - 2) < APIController.getDistance(coordinates) && APIController.getDistance(coordinates) < (double) (8110 + 2));
    }

}

package com.sabre.frequentflyer.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ManagementApiController {
    private static String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFUSXlPRVl6UXpCQk9VVTJRakpHUlVNNFFqWTROelZFT0ROQ1JEZEZSRUpDTXpZeVJVRXlPQSJ9.eyJpc3MiOiJodHRwczovL2ZyZXF1ZW50LWZseWVyLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiI5Rkk0S0U5aEFicUdsdmxaQ3Q2dUdETTdsZVlhSjBSb0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9mcmVxdWVudC1mbHllci5ldS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTUxNjk4NTczNSwiZXhwIjoxNTE3MDcyMTM1LCJhenAiOiI5Rkk0S0U5aEFicUdsdmxaQ3Q2dUdETTdsZVlhSjBSbyIsInNjb3BlIjoicmVhZDpjbGllbnRfZ3JhbnRzIGNyZWF0ZTpjbGllbnRfZ3JhbnRzIGRlbGV0ZTpjbGllbnRfZ3JhbnRzIHVwZGF0ZTpjbGllbnRfZ3JhbnRzIHJlYWQ6dXNlcnMgdXBkYXRlOnVzZXJzIGRlbGV0ZTp1c2VycyBjcmVhdGU6dXNlcnMgcmVhZDp1c2Vyc19hcHBfbWV0YWRhdGEgdXBkYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBkZWxldGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOnRlbmFudF9zZXR0aW5ncyB1cGRhdGU6dGVuYW50X3NldHRpbmdzIHJlYWQ6bG9ncyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgcmVhZDplbWFpbF90ZW1wbGF0ZXMgY3JlYXRlOmVtYWlsX3RlbXBsYXRlcyB1cGRhdGU6ZW1haWxfdGVtcGxhdGVzIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.I9hNogh3VKWdgBv-sanYHq50QVddTn5fNlRZmsSKPWq-wtQcIUUxt1U0Zqgo65rY76qFzLqV0AOkopSZVGJZ23aQK52YieyQUQ4AtOAksHEagvfSH6RTtJ7I5lPi4CoJ_2iUqdgwmLCxN4HWafjCtCWWuJJLvNOIh6t0emLmEslB5pTE83j8e2vS715Do1HEBCu-rzXdLjPc6xhdPYD0jBIQ4LNFkXNhzRwoYCCKXLUKwfSqOrebXOyvZHZUKPu18cRF2kZubzD4SJ1pwcKN-9PLcFLSOiegpMucBcmhf-3svKBqY0ZnhL3R7rz2C6GlGg6tJXOqMlKOZCyYO-Um2g";

    public static String updateUser(String userID, String name, String address) {
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
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}

package com.sabre.frequentflyer.api;

import lombok.Data;

@Data
final class AccessToken {
    /**
     * -- GETTER --
     * Returns <code>String</code> object that can be used to connect with
     * GeoCode API.
     *
     * @return <code>String</code> object with the access token
     * -- SETTER --
     * Sets the access token.
     *
     * @param accessToken the String of the access token
     */
    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
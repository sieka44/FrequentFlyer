package com.sabre.frequentflyer.api;

import lombok.Data;

@Data
final class AccessToken {
    /**
     * -- GETTER --
     * Returns <code>String</code> object that can be used to connect with
     * GeoCode API.
     * -- SETTER --
     * Sets the access token.
     */
    private String access_token;
    /**
     * -- GETTER --
     * Returns <code>String</code> of token type
     * -- SETTER --
     * Sets the token type.
     */
    private String token_type;
    /**
     * -- GETTER --
     * Returns time after which token expires
     * -- SETTER --
     * Sets the expire time.
     */
    private long expires_in;
}
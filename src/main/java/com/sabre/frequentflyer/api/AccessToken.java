package com.sabre.frequentflyer.api;

public final class AccessToken {
    private String access_token;
    private String token_type;
    private long expires_in;


    /**
     * Returns <code>String</code> object that can be used to connect with
     * GeoCode API.
     *
     * @return returns String object with the access token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * Sets the access token.
     *
     * @param access_token the String of the access token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return access_token + "\n " + token_type + "\n " + expires_in;
    }

}
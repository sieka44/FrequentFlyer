package com.sabre.frequentflyer.api;

final class AccessToken {
    /**
     * Access token used to connect with API.
     */
    private String access_token;
    /**
     * Type of token.
     */
    private String token_type;
    /**
     * Time to expire.
     */
    private long expires_in;

    /**
     * -- GETTER --
     * <code>String</code> object that can be used to connect with GeoCode API.
     */
    public String getAccess_token() {
        return this.access_token;
    }

    /**
     * -- GETTER --
     * Returns <code>String</code> of token type
     */
    public String getToken_type() {
        return this.token_type;
    }

    /**
     * -- GETTER --
     * Returns time after which token expires
     */
    public long getExpires_in() {
        return this.expires_in;
    }

    /**
     * -- SETTER --
     * Sets new value of the access token.
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * -- SETTER --
     * Sets the token type.
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    /**
     * -- SETTER --
     * Sets the expire time.
     */
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AccessToken)) return false;
        final AccessToken other = (AccessToken) o;
        final Object this$access_token = this.getAccess_token();
        final Object other$access_token = other.getAccess_token();
        if (this$access_token == null ? other$access_token != null : !this$access_token.equals(other$access_token))
            return false;
        final Object this$token_type = this.getToken_type();
        final Object other$token_type = other.getToken_type();
        if (this$token_type == null ? other$token_type != null : !this$token_type.equals(other$token_type))
            return false;
        if (this.getExpires_in() != other.getExpires_in()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $access_token = this.getAccess_token();
        result = result * PRIME + ($access_token == null ? 43 : $access_token.hashCode());
        final Object $token_type = this.getToken_type();
        result = result * PRIME + ($token_type == null ? 43 : $token_type.hashCode());
        final long $expires_in = this.getExpires_in();
        result = result * PRIME + (int) ($expires_in >>> 32 ^ $expires_in);
        return result;
    }

    public String toString() {
        return "AccessToken(access_token=" + this.getAccess_token() + ", token_type=" + this.getToken_type() + ", expires_in=" + this.getExpires_in() + ")";
    }
}
package com.sabre.frequentflyer.api;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FFAPIConfig {

//    @Value("${com.api.domain}")
//    private String domain;
//    @Value("${com.api.clientSecret}")
//    private String clientSecret;

    private final static String domain = "V1:56tl4jc7qge5xhb7:DEVCENTER:EXT";
    private final static String clientSecret = "GPmcw02H";

    public String getDomain() {
        return domain;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}

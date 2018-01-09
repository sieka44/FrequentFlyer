package com.sabre.frequentflyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan(basePackages = "com.sabre.frequentflyer")
@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:api.properties")
})
public class FrequentFlyerApplication {
    public static void main(String[] args) {
		SpringApplication.run(FrequentFlyerApplication.class, args);
	}
}

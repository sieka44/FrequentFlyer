package com.sabre.frequentflyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Starts application.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.sabre.frequentflyer")
@PropertySources({
		@PropertySource("classpath:application.properties")
})
public class FrequentFlyerApplication {
    public static void main(String[] args) {
		SpringApplication.run(FrequentFlyerApplication.class, args);
	}
}

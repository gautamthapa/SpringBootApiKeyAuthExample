package com.gtm.apiKeyexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

@SpringBootApplication
public class ApiKeyexampleApplication {

    public static void main(String[] args) {
        generateApiKey();
        SpringApplication.run(ApiKeyexampleApplication.class, args);
    }

    public static void generateApiKey() {
        String apiKey = new RandomValueStringGenerator(100).generate();
        System.out.println("apiKey: " + apiKey);
    }

}

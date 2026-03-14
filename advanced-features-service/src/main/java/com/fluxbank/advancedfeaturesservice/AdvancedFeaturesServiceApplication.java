package com.fluxbank.advancedfeaturesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdvancedFeaturesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedFeaturesServiceApplication.class, args);
    }

}

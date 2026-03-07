package com.fluxbank.corebankingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoreBankingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreBankingServiceApplication.class, args);
    }

}

package com.fluxbank.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("customer-service", r -> r
                        .path("/api/customers/**")
                        .uri("http://localhost:8081"))

                .route("account-service", r -> r
                        .path("/api/accounts/**")
                        .uri("http://localhost:8082"))

                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .uri("http://localhost:8083"))

                .build();
    }
}
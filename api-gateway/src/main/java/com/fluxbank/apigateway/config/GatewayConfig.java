package com.fluxbank.apigateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5, 10);
        // replenishRate = Normal allowed rate = 10 requests per second
        // burstCapacity = Maximum requests allowed instantly
    }



    @Bean(name = "userKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange ->
                reactor.core.publisher.Mono.just(
                        exchange.getRequest()
                                .getRemoteAddress()
                                .getAddress()
                                .getHostAddress()
                );
    }


    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder,
                                     RedisRateLimiter rateLimiter,
                                     @Qualifier("userKeyResolver") KeyResolver keyResolver) {

        return builder.routes()

                .route("core-banking-service", r -> r
                        .path("/api/**")
                        .filters(f -> f
                                .rewritePath("/api/(?<segment>.*)", "/${segment}")
                                .requestRateLimiter(c -> {

                                    c.setRateLimiter(rateLimiter);
                                    c.setKeyResolver(keyResolver);
                                }))
                        .uri("http://localhost:8081"))

                .build();
    }
}

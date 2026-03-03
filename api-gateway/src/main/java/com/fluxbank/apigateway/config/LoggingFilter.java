package com.fluxbank.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
// GlobalFilter -> Logs every incoming request before it is routed.
public class LoggingFilter implements GlobalFilter {

// print logs to console or log file
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
// ServerWebExchange =  Request || Response || Headers || Path ||  Query params
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {


        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming request: {}", path);

// Filter 1 → Filter 2 → Filter 3 → Route → Microservice (Pass to Next Filter)
        return chain.filter(exchange);

    }
}
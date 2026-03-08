package com.fluxbank.apigateway.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoggingFilterTest {

    @InjectMocks
    private LoggingFilter loggingFilter;

    @Mock
    private GatewayFilterChain chain;

    @Test
    void shouldPassRequestToNextFilter() {

        ServerHttpRequest request = MockServerHttpRequest.get("/test").build();
        ServerWebExchange exchange = MockServerWebExchange.from((MockServerHttpRequest) request);

        when(chain.filter(exchange)).thenReturn(Mono.empty());

        Mono<Void> result = loggingFilter.filter(exchange, chain);

        assertNotNull(result);
        verify(chain).filter(exchange);
    }
}
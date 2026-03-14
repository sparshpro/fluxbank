package com.fluxbank.advancedfeaturesservice.reporting.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class OAuth2FeignConfig {

    private final OAuth2AuthorizedClientManager clientManager;

    public OAuth2FeignConfig(OAuth2AuthorizedClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        return template -> {

            OAuth2AuthorizeRequest request =
                    OAuth2AuthorizeRequest
                            .withClientRegistrationId("keycloak")
                            .principal("fluxbank-client")
                            .build();

            OAuth2AuthorizedClient client =
                    clientManager.authorize(request);

            String accessToken =
                    client.getAccessToken().getTokenValue();

            template.header(
                    "Authorization",
                    "Bearer " + accessToken
            );

        };
    }
}
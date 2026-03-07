package com.fluxbank.corebankingservice.integration.advancedfeatures;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "advanced-features-service", url = "http://localhost:8082")
public interface AdvancedFeaturesClient {

    @GetMapping("/fraud/check/{transactionId}")
    String checkFraud(@PathVariable String transactionId);
}
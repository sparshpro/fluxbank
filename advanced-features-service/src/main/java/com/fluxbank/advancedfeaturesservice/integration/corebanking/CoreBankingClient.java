package com.fluxbank.advancedfeaturesservice.integration.corebanking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@FeignClient(name = "core-banking-service", url = "${services.core-banking.url}")
public interface CoreBankingClient {

    @GetMapping("/accounts/{accountId}/balance")
    BigDecimal getAccountBalance(@PathVariable("accountId") String accountId);

    @PostMapping("/transactions/transfer")
    void transfer(@RequestBody TransferRequest request);
}
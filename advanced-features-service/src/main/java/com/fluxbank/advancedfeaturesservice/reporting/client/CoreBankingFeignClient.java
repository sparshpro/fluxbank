package com.fluxbank.advancedfeaturesservice.reporting.client;

import com.fluxbank.advancedfeaturesservice.reporting.dto.DailyTransactionReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.MonthlyTransactionReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.TransactionHistoryResponse;
import com.fluxbank.advancedfeaturesservice.reporting.dto.TransactionStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(
        name = "core-banking-service",
        url = "http://localhost:8081",
        configuration = OAuth2FeignConfig.class
)
public interface CoreBankingFeignClient {

    @GetMapping("/transactions/account/{accountId}")
    List<TransactionHistoryResponse> getAccountTransactions(
            @PathVariable("accountId") Long accountId
    );

    @GetMapping("/transactions/daily-summary")
    DailyTransactionReport getDailySummary(
            @RequestParam("date") LocalDate date
    );

    @GetMapping("/transactions/monthly-summary")
    MonthlyTransactionReport getMonthlySummary(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    );

    @GetMapping("/transactions/stats")
    TransactionStats getStats();
}
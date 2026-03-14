package com.fluxbank.advancedfeaturesservice.reporting.service;

import com.fluxbank.advancedfeaturesservice.reporting.client.CoreBankingFeignClient;
import com.fluxbank.advancedfeaturesservice.reporting.dto.*;
import com.fluxbank.advancedfeaturesservice.reporting.repository.ReportRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReportService {

    private final ReportRepository repository;
    private final CoreBankingFeignClient coreBankingFeignClient;

    public ReportService(ReportRepository repository,
                         CoreBankingFeignClient coreBankingFeignClient) {
        this.repository = repository;

        this.coreBankingFeignClient = coreBankingFeignClient;
    }

    public DepositSummaryReport getDepositSummary() {

        BigDecimal totalFD = repository.totalFDInvestments();
        BigDecimal totalRD = repository.totalRDInvestments();

        return DepositSummaryReport.builder()
                .totalFDInvestments(totalFD)
                .totalRDInvestments(totalRD)
                .build();
    }

    public List<FraudReport> getFraudSummary() {

        return repository.fraudSummary()
                .stream()
                .map(row -> FraudReport.builder()
                        .ruleTriggered((String) row[0])
                        .totalAlerts((Long) row[1])
                        .build())
                .collect(Collectors.toList());
    }




    public DailyTransactionReport getDailyReport(LocalDate date) {

        return coreBankingFeignClient.getDailySummary(date);
    }

    public MonthlyTransactionReport getMonthlyReport(int year, int month) {
        return coreBankingFeignClient.getMonthlySummary(year, month);
    }

    public TransactionStats getTransactionStats() {
        return coreBankingFeignClient.getStats();
    }
}
package com.fluxbank.advancedfeaturesservice.reporting.service;

import com.fluxbank.advancedfeaturesservice.reporting.dto.DailyTransactionReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.DepositSummaryReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.FraudReport;
import com.fluxbank.advancedfeaturesservice.reporting.repository.ReportRepository;
import com.fluxbank.advancedfeaturesservice.reporting.repository.TransactionReportRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReportService {

    private final ReportRepository repository;
    private final TransactionReportRepository transactionReportRepository;

    public ReportService(ReportRepository repository,
                         TransactionReportRepository transactionReportRepository) {
        this.repository = repository;
        this.transactionReportRepository = transactionReportRepository;
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


    // DAILY TRANSACTION REPORT FROM Transactions DB

    public DailyTransactionReport getDailyTransactionReport(LocalDate date) {

        Object[] result = transactionReportRepository
                .dailyTransactionSummary(date);

        long totalTransactions = ((Number) result[0]).longValue();
        BigDecimal totalAmount = (BigDecimal) result[1];

        return DailyTransactionReport.builder()
                .date(date)
                .totalTransactions(totalTransactions)
                .totalAmount(totalAmount)
                .build();
    }
}
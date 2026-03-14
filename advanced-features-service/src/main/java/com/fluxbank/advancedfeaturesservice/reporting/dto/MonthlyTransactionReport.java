package com.fluxbank.advancedfeaturesservice.reporting.dto;

import java.math.BigDecimal;

public record MonthlyTransactionReport(
        long totalTransactions,
        BigDecimal totalAmount
) {}
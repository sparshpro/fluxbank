package com.fluxbank.corebankingservice.transaction.dto;

import java.math.BigDecimal;

public record MonthlyTransactionSummary(
        long totalTransactions,
        BigDecimal totalAmount
) {}
package com.fluxbank.corebankingservice.transaction.dto;

import java.math.BigDecimal;

public record DailyTransactionSummary(
        long totalTransactions,
        BigDecimal totalAmount
) {}
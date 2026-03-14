package com.fluxbank.advancedfeaturesservice.reporting.dto;

public record TransactionStats(
        long deposits,
        long withdrawals,
        long transfers
) {}
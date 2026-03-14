package com.fluxbank.corebankingservice.transaction.dto;

public record TransactionStats(
        long deposits,
        long withdrawals,
        long transfers
) {}
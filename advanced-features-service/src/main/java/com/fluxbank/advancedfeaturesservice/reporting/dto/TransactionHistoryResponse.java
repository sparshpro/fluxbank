package com.fluxbank.advancedfeaturesservice.reporting.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryResponse(
        Long id,
        Long accountId,
        BigDecimal amount,
        String type,
        LocalDateTime createdAt
) {}
package com.fluxbank.advancedfeaturesservice.reporting.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyTransactionReport {

    private LocalDateTime createdAt = LocalDateTime.now();

    private long totalTransactions;

    private BigDecimal totalAmount;
}
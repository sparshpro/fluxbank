package com.fluxbank.advancedfeaturesservice.reporting.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyTransactionReport {

    private LocalDate date;

    private long totalTransactions;

    private BigDecimal totalAmount;
}
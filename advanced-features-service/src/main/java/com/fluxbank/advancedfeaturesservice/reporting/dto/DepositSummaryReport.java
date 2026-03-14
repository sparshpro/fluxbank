package com.fluxbank.advancedfeaturesservice.reporting.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositSummaryReport {

    private BigDecimal totalFDInvestments;

    private BigDecimal totalRDInvestments;
}
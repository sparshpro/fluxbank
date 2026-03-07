package com.fluxbank.corebankingservice.loan.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LoanResponse {

    private Long loanId;

    private BigDecimal principalAmount;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private BigDecimal emiAmount;

    private String status;

    private LocalDateTime createdAt;

}
package com.fluxbank.corebankingservice.loan.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {

    private Long accountId;

    private BigDecimal amount;

    private Integer tenureMonths;

}

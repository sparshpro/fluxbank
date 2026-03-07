package com.fluxbank.advancedfeaturesservice.fixeddeposit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateFDRequest {

    private String accountId;

    private BigDecimal principalAmount;

    private double interestRate;

    private int tenureMonths;
}
package com.fluxbank.advancedfeaturesservice.recurringdeposit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRDRequest {

    private String accountId;

    private BigDecimal monthlyInstallment;

    private double interestRate;

    private int tenureMonths;
}
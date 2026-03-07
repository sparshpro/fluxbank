package com.fluxbank.advancedfeaturesservice.fraud.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
public class FraudCheckRequest {

    private String accountId;
    private String transactionId;
    private BigDecimal amount;
}
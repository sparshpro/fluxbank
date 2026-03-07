package com.fluxbank.corebankingservice.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {

    private Long userId;
    private String accountType;
    private BigDecimal initialDeposit;
}
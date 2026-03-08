package com.fluxbank.corebankingservice.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferRequest {

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;
}
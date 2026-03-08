package com.fluxbank.corebankingservice.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WithdrawRequest {

    private Long accountId;
    private BigDecimal amount;
}
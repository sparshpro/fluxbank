
package com.fluxbank.corebankingservice.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionHistoryResponse {

    private String reference;
    private String type;
    private BigDecimal amount;
    private String direction;
    private LocalDateTime time;
}
package com.fluxbank.advancedfeaturesservice.fixeddeposit.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class FDResponse {

    private UUID id;

    private String accountId;

    private BigDecimal principalAmount;

    private BigDecimal maturityAmount;

    private LocalDateTime maturityDate;
}
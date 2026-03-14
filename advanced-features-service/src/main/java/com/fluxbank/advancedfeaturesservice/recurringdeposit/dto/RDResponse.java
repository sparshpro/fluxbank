package com.fluxbank.advancedfeaturesservice.recurringdeposit.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RDResponse {

    private UUID id;

    private String accountId;

    private BigDecimal monthlyInstallment;

    private BigDecimal maturityAmount;

    private LocalDateTime maturityDate;
}
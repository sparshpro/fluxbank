package com.fluxbank.advancedfeaturesservice.fixeddeposit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fixed_deposits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String accountId;

    private BigDecimal principalAmount;

    private double interestRate;

    private int tenureMonths;

    private BigDecimal maturityAmount;

    private LocalDateTime startDate;

    private LocalDateTime maturityDate;
}
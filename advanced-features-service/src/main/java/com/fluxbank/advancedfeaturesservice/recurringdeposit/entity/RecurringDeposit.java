package com.fluxbank.advancedfeaturesservice.recurringdeposit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recurring_deposits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String accountId;

    private BigDecimal monthlyInstallment;

    private double interestRate;

    private int tenureMonths;

    private BigDecimal maturityAmount;

    private LocalDateTime startDate;

    private LocalDateTime maturityDate;
}
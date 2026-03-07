package com.fluxbank.advancedfeaturesservice.fraud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fraud_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String accountId;

    private String ruleTriggered;

    private BigDecimal amount;

    private String transactionId;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
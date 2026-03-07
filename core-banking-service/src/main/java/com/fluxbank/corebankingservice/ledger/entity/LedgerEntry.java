package com.fluxbank.corebankingservice.ledger.entity;

import com.fluxbank.corebankingservice.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String entryType;

    private String transactionReference;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
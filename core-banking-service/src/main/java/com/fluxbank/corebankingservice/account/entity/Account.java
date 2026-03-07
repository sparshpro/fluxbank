package com.fluxbank.corebankingservice.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import com.fluxbank.corebankingservice.transaction.entity.Transaction;
import com.fluxbank.corebankingservice.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private String accountType;

    private BigDecimal balance;

    private String status;

    @Version
    private Long version = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<LedgerEntry> ledgerEntries;
}
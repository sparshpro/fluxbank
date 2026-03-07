package com.fluxbank.corebankingservice.transaction.entity;

import com.fluxbank.corebankingservice.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;

    private String transactionReference;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

    private String status;

    private String direction;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
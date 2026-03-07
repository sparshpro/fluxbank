package com.fluxbank.corebankingservice.transaction.mapper;

import com.fluxbank.corebankingservice.transaction.dto.TransactionResponse;
import com.fluxbank.corebankingservice.transaction.entity.Transaction;

public class TransactionMapper {

    public  static TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .amount(transaction.getAmount())
                .time(transaction.getTransactionTime())
                .reference(transaction.getTransactionReference())
                .type(transaction.getTransactionType())
                .build();
    }
}

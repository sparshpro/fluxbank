package com.fluxbank.corebankingservice.account.mapper;

import com.fluxbank.corebankingservice.account.dto.AccountResponse;
import com.fluxbank.corebankingservice.account.entity.Account;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .userId(account.getUser().getId())
                .build();
    }
}
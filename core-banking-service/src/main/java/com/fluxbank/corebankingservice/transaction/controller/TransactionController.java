package com.fluxbank.corebankingservice.transaction.controller;

import com.fluxbank.corebankingservice.transaction.dto.*;
import com.fluxbank.corebankingservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositRequest request){

        return ResponseEntity.ok(transactionService.deposit(request));
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest request){

        return ResponseEntity.ok(transactionService.withdraw(request));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER','CUSTOMER')")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request){

        return ResponseEntity.ok(transactionService.transfer(request));
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','BANK_STAFF','MANAGER')")
    public ResponseEntity<List<TransactionHistoryResponse>> getAccountTransactions(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getAccountTransactions(accountId)
        );
    }


}
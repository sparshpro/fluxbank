package com.fluxbank.corebankingservice.account.controller;

import com.fluxbank.corebankingservice.account.dto.AccountRequest;
import com.fluxbank.corebankingservice.account.dto.AccountResponse;
import com.fluxbank.corebankingservice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody AccountRequest request) {

        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','BANK_STAFF')")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<List<AccountResponse>> getAccountsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(accountService.getAccountsByUser(userId));
    }

    @PutMapping("/{id}/freeze")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<AccountResponse> freezeAccount(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.freezeAccount(id));
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<AccountResponse> closeAccount(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.closeAccount(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }


}
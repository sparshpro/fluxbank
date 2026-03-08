package com.fluxbank.corebankingservice.loan.controller;

import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasRole('BANK_STAFF')")
    public ResponseEntity<LoanResponse> applyLoan(
            @RequestBody LoanRequest request) {

        return ResponseEntity.ok(loanService.applyLoan(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {

        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<List<LoanResponse>> getAllLoans() {

        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BANK_STAFF')")
    public ResponseEntity<LoanResponse> updateLoan(
            @PathVariable Long id,
            @RequestBody LoanRequest request) {

        return ResponseEntity.ok(loanService.updateLoan(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {

        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.approveLoan(id));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<LoanResponse> rejectLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.rejectLoan(id));
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<LoanResponse> closeLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.closeLoan(id));
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('BANK_STAFF','MANAGER')")
    public ResponseEntity<List<LoanResponse>> getLoansByUserId(@PathVariable Long id) {

        return ResponseEntity.ok(loanService.getLoansByUserId(id));
    }
}
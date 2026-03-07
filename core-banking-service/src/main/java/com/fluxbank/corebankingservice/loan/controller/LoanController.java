package com.fluxbank.corebankingservice.loan.controller;

import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public LoanResponse applyLoan(@RequestBody LoanRequest request){
        return loanService.applyLoan(request);
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','BANKER')")
    public List<LoanResponse> getLoans(@PathVariable Long accountId){
        return loanService.getLoans(accountId);
    }
}
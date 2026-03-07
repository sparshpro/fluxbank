package com.fluxbank.corebankingservice.loan.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.entity.Loan;
import com.fluxbank.corebankingservice.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    public LoanResponse applyLoan(LoanRequest request){

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow();

        BigDecimal interestRate = new BigDecimal("10");

        BigDecimal emi = calculateEmi(request.getAmount(),
                interestRate,
                request.getTenureMonths());

        Loan loan = Loan.builder()
                .account(account)
                .principalAmount(request.getAmount())
                .interestRate(interestRate)
                .tenureMonths(request.getTenureMonths())
                .emiAmount(emi)
                .status("APPLIED")
                .createdAt(LocalDateTime.now())
                .build();

        Loan saved = loanRepository.save(loan);

        return mapToResponse(saved);
    }

    public List<LoanResponse> getLoans(Long accountId){
        return loanRepository.findByAccountId(accountId).stream().map(this::mapToResponse).toList();
    }

    private BigDecimal calculateEmi(BigDecimal principal,
                                    BigDecimal rate,
                                    int months){

        BigDecimal monthlyRate = rate.divide(new BigDecimal("1200"));

        BigDecimal emi = principal.multiply(monthlyRate)
                .divide(BigDecimal.ONE.subtract(
                        BigDecimal.ONE.add(monthlyRate)
                                .pow(-months)
                ), 2, BigDecimal.ROUND_HALF_UP);

        return emi;
    }

    private LoanResponse mapToResponse(Loan loan){

        return LoanResponse.builder()
                .loanId(loan.getId())
                .principalAmount(loan.getPrincipalAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .createdAt(loan.getCreatedAt())
                .build();
    }
}

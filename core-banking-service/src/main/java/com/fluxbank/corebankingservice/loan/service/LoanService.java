package com.fluxbank.corebankingservice.loan.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.entity.Loan;
import com.fluxbank.corebankingservice.loan.mapper.LoanMapper;
import com.fluxbank.corebankingservice.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    public LoanResponse applyLoan(LoanRequest request) {

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

        return LoanMapper.toResponse(saved);
    }

    public List<LoanResponse> getLoansByUserId(Long accountId) {
        return loanRepository.findByAccountId(accountId).stream().map(LoanMapper::toResponse).toList();
    }

    public LoanResponse updateLoan(Long id, LoanRequest request) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        BigDecimal interestRate = new BigDecimal("10");

        BigDecimal emi = calculateEmi(request.getAmount(),
                interestRate,
                request.getTenureMonths());

        loan.setPrincipalAmount(request.getAmount());
        loan.setEmiAmount(emi);
        loan.setInterestRate(interestRate);

        Loan updatedLoan = loanRepository.save(loan);

        return LoanMapper.toResponse(updatedLoan);
    }


    public LoanResponse getLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return LoanMapper.toResponse(loan);
    }

    private BigDecimal calculateEmi(BigDecimal principal,
                                    BigDecimal rate,
                                    int months) {

        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);

        BigDecimal factor = BigDecimal.ONE.add(monthlyRate).pow(months);

        return principal
                .multiply(monthlyRate)
                .multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }

    public void deleteLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loanRepository.delete(loan);
    }

    public LoanResponse approveLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("APPROVED");

        Loan updatedLoan = loanRepository.save(loan);

        return LoanMapper.toResponse(updatedLoan);
    }

    public LoanResponse rejectLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("REJECTED");

        Loan updatedLoan = loanRepository.save(loan);

        return LoanMapper.toResponse(updatedLoan);
    }

    public LoanResponse closeLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus("CLOSED");

        Loan updatedLoan = loanRepository.save(loan);

        return LoanMapper.toResponse(updatedLoan);
    }


    public List<LoanResponse> getAllLoans() {
        return loanRepository.findAll().stream().map(LoanMapper::toResponse).toList();
    }
}

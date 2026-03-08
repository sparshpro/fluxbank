package com.fluxbank.corebankingservice.loan.mapper;

import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.entity.Loan;

public class LoanMapper {

    public static LoanResponse toResponse(Loan loan){

        return LoanResponse.builder()
                .principalAmount(loan.getPrincipalAmount())
                .createdAt(loan.getCreatedAt())
                .emiAmount(loan.getEmiAmount())
                .interestRate(loan.getInterestRate())
                .loanId(loan.getId())
                .status(loan.getStatus())
                .tenureMonths(loan.getTenureMonths())
                .build();
    }


}

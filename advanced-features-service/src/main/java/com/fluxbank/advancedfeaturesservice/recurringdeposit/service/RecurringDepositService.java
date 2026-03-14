package com.fluxbank.advancedfeaturesservice.recurringdeposit.service;

import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.CreateRDRequest;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.RDResponse;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.entity.RecurringDeposit;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.repository.RecurringDepositRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Service
public class RecurringDepositService {

    private final RecurringDepositRepository repository;

    public RecurringDepositService(RecurringDepositRepository repository) {
        this.repository = repository;
    }

    public RDResponse createRD(CreateRDRequest request) {

        BigDecimal maturityAmount = calculateMaturity(
                request.getMonthlyInstallment(),
                request.getInterestRate(),
                request.getTenureMonths()
        );

        RecurringDeposit rd = RecurringDeposit.builder()
                .accountId(request.getAccountId())
                .monthlyInstallment(request.getMonthlyInstallment())
                .interestRate(request.getInterestRate())
                .tenureMonths(request.getTenureMonths())
                .maturityAmount(maturityAmount)
                .startDate(LocalDateTime.now())
                .maturityDate(LocalDateTime.now().plusMonths(request.getTenureMonths()))
                .build();

        RecurringDeposit saved = repository.save(rd);

        return RDResponse.builder()
                .id(saved.getId())
                .accountId(saved.getAccountId())
                .monthlyInstallment(saved.getMonthlyInstallment())
                .maturityAmount(saved.getMaturityAmount())
                .maturityDate(saved.getMaturityDate())
                .build();
    }

    private BigDecimal calculateMaturity(
            BigDecimal monthlyInstallment,
            double rate,
            int months
    ) {

        double r = rate / 100.0 / 12;

        double maturity = monthlyInstallment.doubleValue() *
                ((Math.pow(1 + r, months) - 1) / r) *
                (1 + r);

        return BigDecimal.valueOf(maturity).round(MathContext.DECIMAL64);
    }
}
package com.fluxbank.advancedfeaturesservice.fixeddeposit.service;

import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.CreateFDRequest;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.FDResponse;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.entity.FixedDeposit;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.repository.FixedDepositRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Service
public class FixedDepositService {

    private final FixedDepositRepository repository;

    public FixedDepositService(FixedDepositRepository repository) {
        this.repository = repository;
    }

    public FDResponse createFD(CreateFDRequest request) {

        BigDecimal maturityAmount = calculateMaturity(
                request.getPrincipalAmount(),
                request.getInterestRate(),
                request.getTenureMonths()
        );

        FixedDeposit fd = FixedDeposit.builder()
                .accountId(request.getAccountId())
                .principalAmount(request.getPrincipalAmount())
                .interestRate(request.getInterestRate())
                .tenureMonths(request.getTenureMonths())
                .maturityAmount(maturityAmount)
                .startDate(LocalDateTime.now())
                .maturityDate(LocalDateTime.now().plusMonths(request.getTenureMonths()))
                .build();

        FixedDeposit saved = repository.save(fd);

        return FDResponse.builder()
                .id(saved.getId())
                .accountId(saved.getAccountId())
                .principalAmount(saved.getPrincipalAmount())
                .maturityAmount(saved.getMaturityAmount())
                .maturityDate(saved.getMaturityDate())
                .build();
    }

    private BigDecimal calculateMaturity(
            BigDecimal principal,
            double rate,
            int months
    ) {

        double r = rate / 100;
        double t = months / 12.0;
        int n = 4; // quarterly compounding

        double amount = principal.doubleValue() *
                Math.pow((1 + r / n), n * t);

        return BigDecimal.valueOf(amount).round(MathContext.DECIMAL64);
    }
}
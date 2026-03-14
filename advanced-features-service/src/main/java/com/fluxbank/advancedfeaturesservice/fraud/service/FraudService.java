package com.fluxbank.advancedfeaturesservice.fraud.service;

import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckRequest;
import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckResponse;
import com.fluxbank.advancedfeaturesservice.fraud.entity.FraudAlert;
import com.fluxbank.advancedfeaturesservice.fraud.repository.FraudRepository;
import com.fluxbank.advancedfeaturesservice.fraud.rules.FraudRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudService {

    private final List<FraudRule> rules;
    private final FraudRepository fraudRepository;

    public FraudService(List<FraudRule> rules, FraudRepository fraudRepository) {
        this.rules = rules;
        this.fraudRepository = fraudRepository;
    }

    public FraudCheckResponse checkFraud(FraudCheckRequest request) {

        for (FraudRule rule : rules) {

            if (rule.evaluate(request)) {

                FraudAlert alert = FraudAlert.builder()
                        .accountId(request.getAccountId())
                        .transactionId(request.getTransactionId())
                        .amount(request.getAmount())
                        .ruleTriggered(rule.ruleName())
                        .build();

                fraudRepository.save(alert);

                return FraudCheckResponse.builder()
                        .fraudDetected(true)
                        .ruleTriggered(rule.ruleName())
                        .build();
            }
        }

        return FraudCheckResponse.builder()
                .fraudDetected(false)
                .build();
    }
}
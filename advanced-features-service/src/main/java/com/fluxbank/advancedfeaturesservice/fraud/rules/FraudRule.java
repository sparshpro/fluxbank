package com.fluxbank.advancedfeaturesservice.fraud.rules;


import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckRequest;

public interface FraudRule {

    boolean evaluate(FraudCheckRequest request);

    String ruleName();
}
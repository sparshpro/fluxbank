package com.fluxbank.advancedfeaturesservice.fraud.rules;

import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class AmountRule implements FraudRule {

    @Override
    public boolean evaluate(FraudCheckRequest request) {
        return request.getAmount().compareTo(BigDecimal.valueOf(100000)) > 0;
    }


    @Override
    public String ruleName() {
        return "LARGE_TRANSACTION_RULE";
    }
}
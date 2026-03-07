package com.fluxbank.advancedfeaturesservice.fraud.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class FraudCheckResponse {

    private boolean fraudDetected;
    private String ruleTriggered;
}
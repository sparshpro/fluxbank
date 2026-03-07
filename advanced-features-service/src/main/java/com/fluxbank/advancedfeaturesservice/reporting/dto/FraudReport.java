package com.fluxbank.advancedfeaturesservice.reporting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FraudReport {

    private String ruleTriggered;

    private long totalAlerts;
}
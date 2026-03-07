package com.fluxbank.advancedfeaturesservice.fraud.controller;

import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckRequest;
import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckResponse;
import com.fluxbank.advancedfeaturesservice.fraud.service.FraudService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
public class FraudController {

    private final FraudService fraudService;

    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @PostMapping("/check")
    public FraudCheckResponse checkFraud(@RequestBody FraudCheckRequest request) {
        return fraudService.checkFraud(request);
    }
}
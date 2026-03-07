package com.fluxbank.advancedfeaturesservice.recurringdeposit.controller;

import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.CreateRDRequest;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.RDResponse;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.service.RecurringDepositService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rd")
public class RecurringDepositController {

    private final RecurringDepositService rdService;

    public RecurringDepositController(RecurringDepositService rdService) {
        this.rdService = rdService;
    }

    @PostMapping
    public RDResponse createRD(@RequestBody CreateRDRequest request) {
        return rdService.createRD(request);
    }
}
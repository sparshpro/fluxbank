package com.fluxbank.advancedfeaturesservice.recurringdeposit.controller;

import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.CreateRDRequest;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.dto.RDResponse;
import com.fluxbank.advancedfeaturesservice.recurringdeposit.service.RecurringDepositService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rd")
public class RecurringDepositController {

    private final RecurringDepositService rdService;

    public RecurringDepositController(RecurringDepositService rdService) {
        this.rdService = rdService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','BANK_STAFF','MANAGER','ADMIN')")
    public RDResponse createRD(@RequestBody CreateRDRequest request) {
        return rdService.createRD(request);
    }
}
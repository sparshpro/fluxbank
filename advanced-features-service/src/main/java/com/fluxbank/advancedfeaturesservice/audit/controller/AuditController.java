package com.fluxbank.advancedfeaturesservice.audit.controller;

import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogRequest;
import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogResponse;
import com.fluxbank.advancedfeaturesservice.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fluxbank.advanced.audit.entity.AuditLog;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;


    @PostMapping
    public AuditLogResponse logAction(@RequestBody AuditLogRequest request) {
        return auditService.logAction(request);
    }

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditService.getAllLogs();
    }

    @GetMapping("/user/{userId}")
    public List<AuditLog> getUserLogs(@PathVariable String userId) {
        return auditService.getLogsByUser(userId);
    }
}
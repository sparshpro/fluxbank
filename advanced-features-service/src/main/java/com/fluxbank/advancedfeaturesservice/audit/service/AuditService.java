package com.fluxbank.advancedfeaturesservice.audit.service;


import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogRequest;
import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogResponse;
import com.fluxbank.advancedfeaturesservice.audit.entity.AuditLog;
import com.fluxbank.advancedfeaturesservice.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditLogResponse logAction(AuditLogRequest request) {

        AuditLog log = AuditLog.builder()
                .userId(request.getUserId())
                .action(request.getAction())
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .details(request.getDetails())
                .build();

        AuditLog saved = auditRepository.save(log);

        return AuditLogResponse.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .action(saved.getAction())
                .entityType(saved.getEntityType())
                .entityId(saved.getEntityId())
                .details(saved.getDetails())
                .timestamp(saved.getTimestamp())
                .build();
    }

    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }

    public List<AuditLog> getLogsByUser(String userId) {
        return auditRepository.findByUserId(userId);
    }
}
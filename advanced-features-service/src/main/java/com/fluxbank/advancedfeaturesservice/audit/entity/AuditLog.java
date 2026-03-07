// src/main/java/com/fluxbank/advanced/audit/entity/AuditLog.java

package com.fluxbank.advanced.audit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    private String action;

    private String entityType;

    private String entityId;

    private String details;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
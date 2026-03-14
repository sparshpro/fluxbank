
package com.fluxbank.advancedfeaturesservice.audit.repository;

import com.fluxbank.advancedfeaturesservice.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByUserId(String userId);

}
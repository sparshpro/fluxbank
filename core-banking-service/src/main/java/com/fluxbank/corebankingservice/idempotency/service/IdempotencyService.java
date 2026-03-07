package com.fluxbank.corebankingservice.idempotency.service;

import com.fluxbank.corebankingservice.idempotency.entity.IdempotentRequest;
import com.fluxbank.corebankingservice.idempotency.repository.IdempotentRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotentRequestRepository repository;

    public boolean isDuplicate(String key) {
        return repository.findByIdempotencyKey(key).isPresent();
    }

    public void saveKey(String key) {
        IdempotentRequest request = IdempotentRequest.builder()
                .idempotencyKey(key)
                .status("PROCESSED")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(request);
    }
}
package com.fluxbank.corebankingservice.idempotency.repository;

import com.fluxbank.corebankingservice.idempotency.entity.IdempotentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IdempotentRequestRepositoryTest {

    @Autowired
    private IdempotentRequestRepository repository;

    @Test
    void shouldSaveIdempotentRequest() {

        IdempotentRequest req = IdempotentRequest.builder()
                .idempotencyKey("KEY-1")
                .status("SUCCESS")
                .createdAt(LocalDateTime.now())
                .build();

        IdempotentRequest saved = repository.save(req);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindByIdempotencyKey() {

        IdempotentRequest req = repository.save(
                IdempotentRequest.builder()
                        .idempotencyKey("KEY-123")
                        .status("SUCCESS")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Optional<IdempotentRequest> found =
                repository.findByIdempotencyKey("KEY-123");

        assertThat(found).isPresent();
    }
}
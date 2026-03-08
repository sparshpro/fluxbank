package com.fluxbank.corebankingservice.idempotency.service;

import com.fluxbank.corebankingservice.idempotency.entity.IdempotentRequest;
import com.fluxbank.corebankingservice.idempotency.repository.IdempotentRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdempotencyServiceTest {

    @Mock
    private IdempotentRequestRepository repository;

    @InjectMocks
    private IdempotencyService service;

    @Test
    void shouldDetectDuplicateRequest() {

        when(repository.findByIdempotencyKey("KEY-1"))
                .thenReturn(Optional.of(new IdempotentRequest()));

        boolean exists = service.isDuplicate("KEY-1");

        assertThat(exists).isTrue();
    }
}
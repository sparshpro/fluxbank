package com.fluxbank.advancedfeaturesservice.fraud.rules;

import com.fluxbank.advancedfeaturesservice.fraud.dto.FraudCheckRequest;
import com.fluxbank.advancedfeaturesservice.fraud.repository.FraudRepository;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Order(2)
public class VelocityRule implements FraudRule {

    private static final int MAX_TXN = 5;
    private static final int WINDOW_SECONDS = 10;
    private final StringRedisTemplate redisTemplate;

    public VelocityRule(FraudRepository fraudRepository, StringRedisTemplate redisTemplate) {
        this.redisTemplate  = redisTemplate;
    }

    @Override
    public boolean evaluate(FraudCheckRequest request) {

        String key = "txn:velocity:" + request.getAccountId();

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
        }

        return count != null && count >= MAX_TXN;
    }

    @Override
    public String ruleName() {
        return "VELOCITY_RULE";
    }
}
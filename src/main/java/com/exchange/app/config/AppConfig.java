package com.exchange.app.config;

import com.exchange.app.repository.ExchangeRepository;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@DependsOn
public class AppConfig {

    @Autowired
    ExchangeRepository exchangeRepository;

    AtomicBoolean isIncrease = new AtomicBoolean(true);

    @Scheduled(fixedDelay = 20000)
    void recalculation() {
        boolean isIncrease = this.isIncrease.get();
        double boundedRandomValue;
        if (isIncrease) {
            boundedRandomValue = ThreadLocalRandom.current().nextDouble(1, 1.05);
        } else {
            boundedRandomValue = ThreadLocalRandom.current().nextDouble(0.995, 1.0);
        }
        Map<String, BigDecimal> exchangeRateMap = exchangeRepository.getExchangeRateMap();

        for (Map.Entry<String, BigDecimal> value : exchangeRateMap.entrySet()) {
            BigDecimal rate = value.getValue();
            if(rate == null) {
                continue;
            }
            BigDecimal newRate = rate.multiply(BigDecimal.valueOf(boundedRandomValue))
                    .setScale(2,RoundingMode.HALF_EVEN);
            exchangeRepository.putCourse(value.getKey(), newRate);
        }
        this.isIncrease.set(!isIncrease);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeObjectMapper() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    }
}

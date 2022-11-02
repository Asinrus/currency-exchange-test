package com.exchange.app.repository;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.exchange.app.utils.ExchangeUtils.*;

@Repository
public class ExchangeRepository {


    private final List<String> FOREIGN_CURRENCIES = Arrays.asList(EUR, RUB, USD);
    private final List<String> CURRENCIES =  Arrays.asList(EUR, RUB, USD, AMD);

    private final Map<String, BigDecimal> exchangeRate = new ConcurrentHashMap<>();

    public ExchangeRepository() {
        exchangeRate.put(RUB, new BigDecimal("6.59"));
        exchangeRate.put(EUR, new BigDecimal("394.35"));
        exchangeRate.put(USD, new BigDecimal("403.13"));
        exchangeRate.put(AMD, BigDecimal.ONE);
    }


    public BigDecimal getCourse(String currencyName) {
        return exchangeRate.get(currencyName);
    }


    public void putCourse(String currencyName, BigDecimal newValue) {
        exchangeRate.put(currencyName, newValue);
    }

    public List<String> getCurrencies() {
        return CURRENCIES;
    }

    public Map<String, BigDecimal> getExchangeRateMap() {
        Map<String , BigDecimal> exchangeRateMap = new HashMap<>();
        for(String currency: FOREIGN_CURRENCIES) {
            exchangeRateMap.put(currency, exchangeRate.get(currency));
        }
        return exchangeRateMap;
    }
}

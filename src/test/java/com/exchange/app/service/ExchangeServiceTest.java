package com.exchange.app.service;

import com.exchange.app.model.ExchangeRequest;
import com.exchange.app.model.ExchangeResponse;
import com.exchange.app.repository.ExchangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static com.exchange.app.utils.ExchangeUtils.*;

class ExchangeServiceTest {
    ExchangeRepository exchangeRepo = new ExchangeRepository();
    private ExchangeService exchangeService = new ExchangeService(exchangeRepo);


    @Test
    void correctExchange() {
        ExchangeRequest req = new ExchangeRequest(AMD, RUB, BigDecimal.ONE);
        ExchangeResponse response = exchangeService.exchange(req);
        Assertions.assertEquals(response.getDestinationCurrencyAmount(), exchangeRepo.getCourse(RUB));
    }

    @Test
    void correctExchangeInNotBaseCurrency() {
        ExchangeRequest req = new ExchangeRequest(EUR, RUB, BigDecimal.ONE);
        ExchangeResponse response = exchangeService.exchange(req);
        Assertions.assertTrue(response.getDestinationCurrencyAmount().compareTo(BigDecimal.ONE) > 0);
    }

    @Test
    void exchangeWithIncorrectCurrency() {
        ExchangeRequest req = new ExchangeRequest("FAKE", RUB, BigDecimal.ONE);
        Assertions.assertThrowsExactly(HttpClientErrorException.class,
                () -> exchangeService.exchange(req));
    }

    @Test
    void exchangeWithNullInValues() {
        ExchangeRequest req = new ExchangeRequest(AMD, RUB, null);
        Assertions.assertThrowsExactly(HttpClientErrorException.class, () -> exchangeService.exchange(req));
    }

    @Test
    void exchangeCurrencyToCurrencyGet1() {
        ExchangeRequest req = new ExchangeRequest(RUB, RUB, BigDecimal.ONE);
        ExchangeResponse response = exchangeService.exchange(req);
        Assertions.assertTrue(response.getDestinationCurrencyAmount().compareTo(BigDecimal.ONE) == 0);
    }


}
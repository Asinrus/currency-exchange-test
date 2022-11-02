package com.exchange.app.service;

import com.exchange.app.model.ExchangeRequest;
import com.exchange.app.model.ExchangeResponse;
import com.exchange.app.repository.ExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.exchange.app.utils.ExchangeUtils.AMD;

@Service
@AllArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    public ExchangeResponse exchange(ExchangeRequest request) {
        BigDecimal rate;
        BigDecimal ratio;
        String destinationCurrency = removeWithNullCheck(request::getDestinationCurrency);
        BigDecimal sourceCurrencyAmount = removeWithNullCheck(request::getSourceCurrencyAmount);
        if (request.getSourceCurrency().equals(AMD)) {
            ratio = exchangeRepository.getCourse(destinationCurrency);
        } else {
            BigDecimal currentSourceRate = removeFromInsideWithNullCheck(() ->
                    exchangeRepository.getCourse(request.getSourceCurrency()));

            BigDecimal currentDestinationRate = removeFromInsideWithNullCheck(() ->
                    exchangeRepository.getCourse(destinationCurrency));
            ratio = currentSourceRate.divide(currentDestinationRate, RoundingMode.HALF_EVEN)
                    .setScale(2,RoundingMode.HALF_EVEN);
        }

        rate = sourceCurrencyAmount.multiply(ratio).setScale(2,RoundingMode.HALF_EVEN);
        return new ExchangeResponse(request.getSourceCurrency(), destinationCurrency,
                sourceCurrencyAmount, rate);
    }

    public List<String> getAvailableCurrencies() {
        return exchangeRepository.getCurrencies();
    }

    public Map<String, BigDecimal> getCurrentRate() {
        return exchangeRepository.getExchangeRateMap();
    }

    private <T> T removeWithNullCheck(Supplier<T> supplier) {
        T value = supplier.get();
        if (value == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return value;
    }

    private <T> T removeFromInsideWithNullCheck(Supplier<T> supplier) {
        T value = supplier.get();
        if (value == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return value;
    }

}

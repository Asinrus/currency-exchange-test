package com.exchange.app.controller;

import com.exchange.app.model.ExchangeRequest;
import com.exchange.app.model.ExchangeResponse;
import com.exchange.app.service.ExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api("Exchange api")
@RestController
@RequestMapping("/exchange")
@AllArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @ApiOperation("Get current exchange rate. The information is refreshed every 20 seconds")
    @GetMapping("/rate")
    public Map<String, BigDecimal> getCurrentRate() {
        return exchangeService.getCurrentRate();
    }

    @ApiOperation("Get available currencies ready to exchange")
    @GetMapping("/currencies")
    public List<String> getCurrencies(){
        return exchangeService.getAvailableCurrencies();
    }

    @ApiOperation("Exchange currency from one to another")
    @PostMapping
    public ExchangeResponse exchange(
            @ApiParam(value = "application/json",
            example = "{\"destinationCurrency\": \"USD\", \"sourceCurrency\": \"RUB\", \"sourceCurrencyAmount\": 1234.57}")
            @RequestBody ExchangeRequest request) {
        return exchangeService.exchange(request);
    }
}

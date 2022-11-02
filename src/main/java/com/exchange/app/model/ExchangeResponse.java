package com.exchange.app.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ExchangeResponse {
    @ApiModelProperty(notes = "Currency is needed to exchange", required = true, example = "RUB")
    private final String sourceCurrency;
    @ApiModelProperty(notes = "Currency is needed to get", required = true, example = "USD")
    private final String destinationCurrency;
    @ApiModelProperty(notes = "Amount of currency is needed to exchange", required = true,
            example =  "1234.57",
            dataType = "double")
    private final BigDecimal sourceCurrencyAmount;
    @ApiModelProperty(notes = "Amount of currency to get after exchange", required = true,
            example =  "1234.57",
            dataType = "double")
    private final BigDecimal destinationCurrencyAmount;
}

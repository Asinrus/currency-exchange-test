package com.exchange.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "request to exchange")
public class ExchangeRequest {
    @ApiModelProperty(notes = "Currency is needed to exchange", required = true, example = "RUB")
    private final String sourceCurrency;
    @ApiModelProperty(notes = "Currency is needed to get", required = true, example = "USD")
    private final String destinationCurrency;
    @ApiModelProperty(notes = "Amount of currency is needed to exchange", required = true,
            example =  "1234.57",
            dataType = "double")
    private final BigDecimal sourceCurrencyAmount;
}

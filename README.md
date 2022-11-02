# currency-exchange-test

Exchange-currency test task:
* One user = admin/admin
* JWT authentication

swagger-ui - http://localhost:8080/swagger-ui.html

Commands for fast launch. Work for Ubuntu:

<code>export TOKEN=`curl -XPOST admin:admin@localhost:8080/auth/login`</code>

List of available currencies to exchange:

<code>curl -H "Authorization: Bearer $TOKEN" -XGET localhost:8080/exchange/currencies && echo</code>

Current exchange rate:

<code>curl -H "Authorization: Bearer $TOKEN" -XGET localhost:8080/exchange/rate && echo</code>

Exchange action:

<code>curl -H "Authorization: Bearer $TOKEN" -XPOST -H "Content-Type: application/json" \
--data '{"destinationCurrency": "USD", "sourceCurrency": "RUB", "sourceCurrencyAmount": 1234.57}' \
 localhost:8080/exchange/ && echo</code>

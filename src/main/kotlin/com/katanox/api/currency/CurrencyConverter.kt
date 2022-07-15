package com.katanox.api.currency

import java.math.BigDecimal

interface CurrencyConverter {

    fun convert(from: Currency, to: Currency, amount: BigDecimal): BigDecimal
}
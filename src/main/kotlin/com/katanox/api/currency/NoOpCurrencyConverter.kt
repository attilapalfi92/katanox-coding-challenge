package com.katanox.api.currency

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class NoOpCurrencyConverter : CurrencyConverter {

    override fun convert(from: Currency, to: Currency, amount: BigDecimal): BigDecimal {
        return amount
    }
}
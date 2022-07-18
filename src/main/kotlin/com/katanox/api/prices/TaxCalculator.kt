package com.katanox.api.prices

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class TaxCalculator {

    fun applyVat(beforeTax: BigDecimal, vat: BigDecimal): BigDecimal {
        return beforeTax.multiply(
            vat.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE)
        )
    }
}
package com.katanox.api.prices

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class TaxCalculatorTest {

    private val taxCalculator = TaxCalculator()

    @Test
    fun `test applyVat applies VAT correctly`() {
        val result = taxCalculator.applyVat(BigDecimal.valueOf(50), BigDecimal.valueOf(20))

        assertEquals(BigDecimal.valueOf(1.2 * 50), result)
    }

    @Test
    fun `test applyVat applies VAT correctly with fractions`() {
        val result = taxCalculator.applyVat(BigDecimal.valueOf(51.2), BigDecimal.valueOf(17.5))

        assertEquals(BigDecimal.valueOf(60.16), result.stripTrailingZeros())
    }
}
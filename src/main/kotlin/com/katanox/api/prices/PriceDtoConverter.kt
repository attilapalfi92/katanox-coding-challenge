package com.katanox.api.prices

import com.katanox.test.sql.tables.Prices
import org.jooq.Record
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PriceDtoConverter(
    private val taxCalculator: TaxCalculator
) {
    fun recordToPriceDto(record: Record, vat: BigDecimal): PriceDto {
        val priceBeforeTax = record.getValue(Prices.PRICES.PRICE_BEFORE_TAX)
        val priceAfterTax = taxCalculator.applyVat(priceBeforeTax, vat)
        return PriceDto(
            date = record.getValue(Prices.PRICES.DATE),
            quantity = record.getValue(Prices.PRICES.QUANTITY),
            roomId = record.getValue(Prices.PRICES.ROOM_ID),
            priceBeforeTax = priceBeforeTax,
            priceAfterTax = priceAfterTax,
            currency = record.getValue(Prices.PRICES.CURRENCY)
        )
    }
}
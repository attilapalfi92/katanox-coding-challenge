package com.katanox.api.prices

import com.katanox.test.sql.tables.Prices
import java.math.BigDecimal
import java.time.LocalDate
import org.jooq.Record

data class PriceDto(
    val date: LocalDate,
    val quantity: Int,
    val roomId: Long,
    val priceBeforeTax: BigDecimal,
    val currency: String
): Comparable<PriceDto> {

    companion object {
        fun ofRecord(record: Record) = PriceDto(
            date = record.getValue(Prices.PRICES.DATE),
            quantity = record.getValue(Prices.PRICES.QUANTITY),
            roomId = record.getValue(Prices.PRICES.ROOM_ID),
            priceBeforeTax = record.getValue(Prices.PRICES.PRICE_BEFORE_TAX),
            currency = record.getValue(Prices.PRICES.CURRENCY)
        )
    }

    override fun compareTo(other: PriceDto): Int {
        return date.compareTo(other.date)
    }
}
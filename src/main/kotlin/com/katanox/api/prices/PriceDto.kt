package com.katanox.api.prices

import java.math.BigDecimal
import java.time.LocalDate

data class PriceDto(
    val date: LocalDate,
    val quantity: Int,
    val roomId: Long,
    val priceBeforeTax: BigDecimal,
    val priceAfterTax: BigDecimal,
    val currency: String
) : Comparable<PriceDto> {

    override fun compareTo(other: PriceDto): Int {
        return date.compareTo(other.date)
    }
}
package com.katanox.api.booking

import com.katanox.api.currency.Currency
import com.katanox.api.guest.Guest
import com.katanox.api.payment.Payment
import java.math.BigDecimal

data class BookingDto(
    val hotelId: Long,
    val roomId: Long,
    val priceBeforeTax: BigDecimal,
    val priceAfterTax: BigDecimal,
    val currency: Currency,
    val guest: Guest,
    val payment: Payment
)
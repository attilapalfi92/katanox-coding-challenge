package com.katanox.api.booking

import com.katanox.api.currency.Currency
import com.katanox.api.guest.Guest
import com.katanox.api.payment.Payment
import java.math.BigDecimal
import java.time.LocalDate


data class BookingRequest(
    val hotelId: Long,
    val roomId: Long,
    val priceBeforeTax: BigDecimal,
    val priceAfterTax: BigDecimal,
    val currency: Currency,
    val checkin: LocalDate,
    val checkout: LocalDate,
    val guest: Guest,
    val payment: Payment,
)

data class BookingResponse(val bookingId: Long)
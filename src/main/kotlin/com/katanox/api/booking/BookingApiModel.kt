package com.katanox.api.booking

import com.katanox.api.booking.payment.Payment
import com.katanox.api.guest.Guest
import java.math.BigDecimal


data class BookingRequest (
    val hotelId: Long,
    val roomId: Long,
    val priceBeforeTax: BigDecimal,
    val priceAfterTax: BigDecimal,
    val currency: String,
    val guest: Guest,
    val payment: Payment,
)

data class BookingResponse(val bookingId: Long)
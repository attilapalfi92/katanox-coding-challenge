package com.katanox.api.booking

import com.katanox.api.booking.payment.Payment
import com.katanox.api.guest.Guest

data class BookingRequest (
    val hotelId: Long,
    val roomId: Long,
    val price: Long,
    val currency: String,
    val guest: Guest,
    val payment: Payment,
)
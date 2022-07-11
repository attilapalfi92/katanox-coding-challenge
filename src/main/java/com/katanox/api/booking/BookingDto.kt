package com.katanox.api.booking

import com.katanox.api.guest.Guest

data class BookingDto(
    val hotelId: Long,
    val roomId: Long,
    val price_before_tax: Long,
    val price_after_tax: Long,
    val currency: String,
    val guest: Guest,
    val payment: Payment
)
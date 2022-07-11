package com.katanox.api.booking.payment

data class Payment (
    val card_holder: String,
    val card_number: String,
    val cvv: String,
    val expiry_month: String,
    val expiry_year: String,
)
package com.katanox.api.payment

data class Payment (
    val id: Long,
    val cardHolder: String,
    val cardNumber: String,
    val cvv: String,
    val expiryMonth: String,
    val expiryYear: String,
)
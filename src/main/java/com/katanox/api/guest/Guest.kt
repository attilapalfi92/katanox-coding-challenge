package com.katanox.api.guest

import java.time.LocalDate

data class Guest(
    val name: String,
    val surname: String,
    val birthdate: LocalDate,
)
package com.katanox.api.search

import java.time.LocalDate

class SearchRequest (
    val checkin: LocalDate,
    val checkout: LocalDate,
    val hotelId: Long
)
package com.katanox.api.search

import java.math.BigDecimal
import java.time.LocalDate

class SearchRequest(
    val checkin: LocalDate,
    val checkout: LocalDate,
    val hotelId: Long
)

data class SearchResponse(
    val rooms: List<RoomResponse>
)

data class RoomResponse(
    val hotelId: Long,
    val roomId: Long,
    val price: BigDecimal,
    val currency: String
)

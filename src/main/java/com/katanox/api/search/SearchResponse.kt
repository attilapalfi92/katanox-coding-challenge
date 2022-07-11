package com.katanox.api.search

data class SearchResponse (
    val hotelId: Long,
    val roomId: Long,
    val price: Long,
    val currency: String
)
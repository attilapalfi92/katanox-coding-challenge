package com.katanox.api.hotel

import com.katanox.test.sql.tables.Hotels
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class HotelRepository(private val dsl: DSLContext) {

    fun insertHotel() {
        val hotel = Hotels.HOTELS
        dsl.insertInto(hotel, hotel.NAME, hotel.ROOMS)
            .values("fake", 1)
            .execute()
    }
}
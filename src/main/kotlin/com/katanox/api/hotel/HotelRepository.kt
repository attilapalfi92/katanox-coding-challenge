package com.katanox.api.hotel

import com.katanox.test.sql.tables.Hotels
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class HotelRepository(private val dsl: DSLContext) {

    fun getVatForHotel(hotelId: Long): BigDecimal {
        val alma  = dsl.select(Hotels.HOTELS.VAT)
            .from(Hotels.HOTELS)
            .where(Hotels.HOTELS.ID.equal(hotelId))
            .fetch()

        return alma[0].value1()
    }
}
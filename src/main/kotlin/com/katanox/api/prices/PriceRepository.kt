package com.katanox.api.prices

import com.katanox.test.sql.tables.Prices.PRICES
import com.katanox.test.sql.tables.Rooms.ROOMS
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class PriceRepository(private val dsl: DSLContext) {

    fun findPriceRecordsForDatesInHotel(dates: Set<LocalDate>, hotelId: Long): Result<Record> {
        return dsl.select()
            .from(PRICES)
            .join(ROOMS)
            .on(PRICES.ROOM_ID.equal(ROOMS.ID))
            .where(ROOMS.HOTEL_ID.equal(hotelId))
            .and(PRICES.DATE.`in`(dates))
            .and(PRICES.QUANTITY.greaterThan(0))
            .fetch()
    }
}
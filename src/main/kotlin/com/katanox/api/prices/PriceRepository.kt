package com.katanox.api.prices

import com.katanox.test.sql.tables.Prices.PRICES
import com.katanox.test.sql.tables.Rooms.ROOMS
import com.katanox.test.sql.tables.records.PricesRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.jooq.UpdateConditionStep
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Repository
class PriceRepository(private val dsl: DSLContext) {

    fun findPriceRecordsByDatesAndRoom(dates: Set<LocalDate>, roomId: Long): Result<Record> {
        return dsl.select()
            .from(PRICES)
            .where(PRICES.ROOM_ID.equal(roomId))
            .and(PRICES.DATE.`in`(dates))
            .and(PRICES.QUANTITY.greaterThan(0))
            .fetch()
    }

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

    fun decreaseQuantitiesForDatesForRoom(dates: Set<LocalDate>, roomId: Long) {
        val records = findPriceRecordsForDatesForRoom(dates, roomId)
        // TODO: throw a new exception type and use exception mapper to map it to bad request
        if (records.isEmpty() || records.size < dates.size) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Room is not available anymore for the given dates."
            )
        }
        updateRoomQuantities(records)
    }

    private fun findPriceRecordsForDatesForRoom(dates: Set<LocalDate>, roomId: Long): Result<Record> {
        return dsl.select()
            .from(PRICES)
            .where(PRICES.ROOM_ID.equal(roomId))
            .and(PRICES.DATE.`in`(dates))
            .and(PRICES.QUANTITY.greaterThan(0))
            .fetch()
    }

    private fun updateRoomQuantities(records: Result<Record>) {
        val updates: MutableList<UpdateConditionStep<PricesRecord>> = ArrayList()
        records.forEach {
            updates.add(
                dsl.update(PRICES)
                    .set(PRICES.QUANTITY, it[PRICES.QUANTITY] - 1)
                    .where(PRICES.ROOM_ID.eq(it[PRICES.ROOM_ID]))
                    .and(PRICES.DATE.eq(it[PRICES.DATE]))
            )
        }

        dsl.batch(updates).execute()
    }
}

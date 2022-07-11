package com.katanox.api.room

import com.katanox.test.sql.tables.Rooms.ROOMS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class RoomRepository(private val dsl: DSLContext) {

    fun findRooms(checkin: LocalDate, checkout: LocalDate, hotelId: Long) {
        dsl.select()
            .from(ROOMS)
            .where(ROOMS.NUMBER_OF_THIS_TYPE.greaterThan(0))
    }
}
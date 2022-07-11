package com.katanox.api.charges.flat

import com.katanox.test.sql.tables.ExtraChargesFlat.EXTRA_CHARGES_FLAT
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class FlatChargeRepository(private val dsl: DSLContext) {

    fun findChargesByHotel(hotelId: Long): Result<Record> {
        return dsl.select()
            .from(EXTRA_CHARGES_FLAT)
            .where(EXTRA_CHARGES_FLAT.HOTEL_ID.equal(hotelId))
            .fetch()
    }
}
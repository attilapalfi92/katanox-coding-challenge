package com.katanox.api.charges.percentage

import com.katanox.test.sql.tables.ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class PercentageChargeRepository(private val dsl: DSLContext) {

    fun findChargesByHotel(hotelId: Long): Result<Record> {
        return dsl.select()
            .from(EXTRA_CHARGES_PERCENTAGE)
            .where(EXTRA_CHARGES_PERCENTAGE.HOTEL_ID.equal(hotelId))
            .fetch()
    }
}
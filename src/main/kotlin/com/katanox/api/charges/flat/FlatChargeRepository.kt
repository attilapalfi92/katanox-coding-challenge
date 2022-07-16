package com.katanox.api.charges.flat

import com.katanox.test.sql.tables.ExtraChargesFlat.EXTRA_CHARGES_FLAT
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class FlatChargeRepository(private val dsl: DSLContext) {

    fun findChargesByHotel(hotelId: Long): Set<FlatChargeDto> {
        return dsl.select()
            .from(EXTRA_CHARGES_FLAT)
            .where(EXTRA_CHARGES_FLAT.HOTEL_ID.equal(hotelId))
            .fetch()
            .map { FlatChargeDto.ofRecord(it) }.toSet()
    }
}
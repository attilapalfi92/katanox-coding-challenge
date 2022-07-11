package com.katanox.api.charges.flat

import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service

@Service
class FlatChargeService(private val repository: FlatChargeRepository) {

    fun findChargesByHotel(hotelId: Long): Set<FlatChargeDto> {
        val records: Result<Record> = repository.findChargesByHotel(hotelId)
        return records.map { FlatChargeDto.ofRecord(it) }.toSet()
    }
}
package com.katanox.api.charges.percentage

import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service

@Service
class PercentageChargeService(private val repository: PercentageChargeRepository) {

    fun findChargesByHotel(hotelId: Long): Set<PercentageChargeDto> {
        val records: Result<Record> = repository.findChargesByHotel(hotelId)
        return records.map { PercentageChargeDto.ofRecord(it) }.toSet()
    }
}
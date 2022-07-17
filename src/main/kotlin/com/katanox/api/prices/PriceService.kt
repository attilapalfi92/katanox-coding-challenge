package com.katanox.api.prices

import com.katanox.api.date.DateConverter
import com.katanox.api.hotel.HotelService
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class PriceService(
    private val dateConverter: DateConverter,
    private val hotelService: HotelService,
    private val priceDtoConverter: PriceDtoConverter,
    private val repository: PriceRepository
) {
    fun findPricesByRoomForDatesInHotel(
        checkin: LocalDate,
        checkout: LocalDate,
        hotelId: Long
    ): Map<Long, Set<PriceDto>> {
        val dates = dateConverter.intervalToDatesOfNights(checkin, checkout)
        val records: Result<Record> = repository.findPriceRecordsForDatesInHotel(dates, hotelId)
        val vat = hotelService.getVatForHotel(hotelId)
        val result = HashMap<Long, Set<PriceDto>>()
        records.map { record -> priceDtoConverter.recordToPriceDto(record, vat) }
            .groupBy({ it.roomId }, { it })
            .forEach { result[it.key] = TreeSet(it.value) }
        return result
    }
}
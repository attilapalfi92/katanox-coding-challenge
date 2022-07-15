package com.katanox.api.search

import com.katanox.api.charges.ExtraChargeService
import com.katanox.api.prices.PriceDto
import com.katanox.api.prices.PriceService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.temporal.ChronoUnit

@Service
class SearchService(
    private val searchValidator: SearchValidator,
    private val priceService: PriceService,
    private val extraChargeService: ExtraChargeService
) {

    fun search(request: SearchRequest): SearchResponse {
        searchValidator.validateSearchRequest(request)
        val hotelId = request.hotelId
        val pricesByRoom: Map<Long, Set<PriceDto>> =
            priceService.findPricesByRoomForDatesInHotel(request.checkin, request.checkout, hotelId)
        val nights = ChronoUnit.DAYS.between(request.checkin, request.checkout).toInt()
        val availableRooms: Map<Long, Set<PriceDto>> = pricesByRoom.filter { it.value.size == nights }

        return SearchResponse(
            rooms = availableRooms.entries.map { entry ->
                val roomPrice = entry.value.sumOf { it.priceBeforeTax }
                RoomResponse(
                    hotelId = hotelId,
                    roomId = entry.key,
                    price = calculateTotalPrice(roomPrice, nights, entry, hotelId),
                    currency = entry.value.first().currency
                )
            }.toList()
        )
    }

    private fun calculateTotalPrice(
        roomPrice: BigDecimal,
        nights: Int,
        entry: Map.Entry<Long, Set<PriceDto>>,
        hotelId: Long
    ): BigDecimal {
        val extraCharges = extraChargeService.calculateCharges(
            numberOfNights = nights,
            firstNightPrice = entry.value.first().priceBeforeTax,
            roomPrice = roomPrice,
            hotelId = hotelId
        )
        return roomPrice.add(extraCharges)
    }
}
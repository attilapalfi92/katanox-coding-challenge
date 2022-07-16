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
            rooms = availableRooms.entries.map { roomPrices ->
                createRoomResponse(roomPrices, hotelId, nights)
            }.toList()
        )
    }

    private fun createRoomResponse(
        roomPrices: Map.Entry<Long, Set<PriceDto>>,
        hotelId: Long,
        nights: Int
    ): RoomResponse {
        val roomPrice = roomPrices.value.sumOf { it.priceBeforeTax }
        val totalPrice = calculateTotalPrice(roomPrice, nights, roomPrices.value.first().priceBeforeTax, hotelId)
        return RoomResponse(
            hotelId = hotelId,
            roomId = roomPrices.key,
            price = totalPrice,
            currency = roomPrices.value.first().currency
        )
    }

    private fun calculateTotalPrice(
        roomPrice: BigDecimal,
        nights: Int,
        firstNightPrice: BigDecimal,
        hotelId: Long
    ): BigDecimal {
        val extraCharges = extraChargeService.calculateCharges(
            numberOfNights = nights,
            firstNightPrice = firstNightPrice,
            roomPrice = roomPrice,
            hotelId = hotelId
        )
        return roomPrice.add(extraCharges)
    }
}
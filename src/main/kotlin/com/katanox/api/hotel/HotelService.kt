package com.katanox.api.hotel

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class HotelService(private val hotelRepository: HotelRepository) {

    fun getVatForHotel(hotelId: Long): BigDecimal {
        return hotelRepository.getVatForHotel(hotelId)
    }
}
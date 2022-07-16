package com.katanox.api.charges.flat

import org.springframework.stereotype.Service

@Service
class FlatChargeService(private val repository: FlatChargeRepository) {

    fun calculateFlatCharges(hotelId: Long, numberOfNights: Int): Double {
        var result = 0.0
        val flatCharges = repository.findChargesByHotel(hotelId)
        result += flatCharges.filter { it.chargeType == FlatChargeType.ONCE }.sumOf { it.price }
        result += flatCharges.filter { it.chargeType == FlatChargeType.PER_NIGHT }
            .sumOf { it.price * numberOfNights }
        return result
    }
}
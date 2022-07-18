package com.katanox.api.charges

import com.katanox.api.charges.flat.FlatChargeService
import com.katanox.api.charges.percentage.PercentageChargeService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExtraChargeService(
    private val flatChargeService: FlatChargeService,
    private val percentageChargeService: PercentageChargeService
) {

    fun calculateCharges(
        numberOfNights: Int,
        firstNightPrice: BigDecimal,
        roomPrice: BigDecimal,
        hotelId: Long
    ): BigDecimal {
        val flatCharges: Double = flatChargeService
            .calculateFlatCharges(hotelId, numberOfNights)

        val percentageCharges = percentageChargeService
            .calculatePercentageCharges(hotelId, firstNightPrice, roomPrice)
        return BigDecimal.valueOf(flatCharges + percentageCharges)
    }
}
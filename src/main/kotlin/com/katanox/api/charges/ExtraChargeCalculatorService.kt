package com.katanox.api.charges

import com.katanox.api.charges.flat.FlatChargeService
import com.katanox.api.charges.flat.FlatChargeType.ONCE
import com.katanox.api.charges.flat.FlatChargeType.PER_NIGHT
import com.katanox.api.charges.percentage.PercentageAppliedOn.FIRST_NIGHT
import com.katanox.api.charges.percentage.PercentageAppliedOn.TOTAL_AMOUNT
import com.katanox.api.charges.percentage.PercentageChargeService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExtraChargeCalculatorService(
    private val flatChargeService: FlatChargeService,
    private val percentageChargeService: PercentageChargeService
) {

    fun calculateCharges(
        numberOfNights: Int,
        firstNightPrice: BigDecimal,
        totalPrice: BigDecimal,
        hotelId: Long
    ): Double {
        var result = 0.0
        val flatCharges = flatChargeService.findChargesByHotel(hotelId)
        val percentageCharges = percentageChargeService.findChargesByHotel(hotelId)

        result += flatCharges.filter { it.chargeType == ONCE }.sumOf { it.price }
        result += flatCharges.filter { it.chargeType == PER_NIGHT }.sumOf { it.price * numberOfNights }
        result += percentageCharges.filter { it.appliedOn == FIRST_NIGHT }.sumOf {
            firstNightPrice.toDouble() * (1 + (it.percentage / 100))
        }
        result += percentageCharges.filter { it.appliedOn == TOTAL_AMOUNT }
            .sumOf { 1 + (it.percentage / 100) * totalPrice.toDouble() }

        return result
    }

}
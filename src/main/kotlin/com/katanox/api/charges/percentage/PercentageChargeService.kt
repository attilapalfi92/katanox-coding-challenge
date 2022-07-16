package com.katanox.api.charges.percentage

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PercentageChargeService(private val repository: PercentageChargeRepository) {

    fun calculatePercentageCharges(hotelId: Long, firstNightPrice: BigDecimal, totalAmount: BigDecimal): Double {
        val percentageCharges: Set<PercentageChargeDto> = repository.findChargesByHotel(hotelId)
        var result = calculateFirstNightCharges(percentageCharges, firstNightPrice)
        result += calculateTotalAmountCharges(percentageCharges, totalAmount)
        return result
    }

    private fun calculateFirstNightCharges(
        percentageCharges: Set<PercentageChargeDto>,
        firstNightPrice: BigDecimal
    ): Double {
        return percentageCharges.filter { it.appliedOn == PercentageAppliedOn.FIRST_NIGHT }
            .sumOf { (it.percentage / 100) * firstNightPrice.toDouble() }
    }

    private fun calculateTotalAmountCharges(
        percentageCharges: Set<PercentageChargeDto>,
        totalAmount: BigDecimal
    ): Double {
        return percentageCharges.filter { it.appliedOn == PercentageAppliedOn.TOTAL_AMOUNT }
            .sumOf { (it.percentage / 100) * totalAmount.toDouble() }
    }
}
package com.katanox.api.charges.percentage

import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PercentageChargeService(private val repository: PercentageChargeRepository) {

    fun calculatePercentageCharges(hotelId: Long, firstNightPrice: BigDecimal, totalAmount: BigDecimal): Double {
        val percentageCharges = findChargesByHotel(hotelId)
        var result = calculateFirstNightCharges(percentageCharges, firstNightPrice)
        result += calculateTotalAmountCharges(percentageCharges, totalAmount)
        return result
    }

    private fun calculateFirstNightCharges(
        percentageCharges: Set<PercentageChargeDto>,
        firstNightPrice: BigDecimal
    ): Double {
        return percentageCharges.filter { it.appliedOn == PercentageAppliedOn.FIRST_NIGHT }
            .sumOf { firstNightPrice.toDouble() * (1 + (it.percentage / 100)) }
    }

    private fun calculateTotalAmountCharges(
        percentageCharges: Set<PercentageChargeDto>,
        totalAmount: BigDecimal
    ): Double {
        return percentageCharges.filter { it.appliedOn == PercentageAppliedOn.TOTAL_AMOUNT }
            .sumOf { 1 + (it.percentage / 100) * totalAmount.toDouble() }
    }

    private fun findChargesByHotel(hotelId: Long): Set<PercentageChargeDto> {
        val records: Result<Record> = repository.findChargesByHotel(hotelId)
        return records.map { PercentageChargeDto.ofRecord(it) }.toSet()
    }
}
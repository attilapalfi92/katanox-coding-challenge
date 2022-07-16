package com.katanox.api.charges.percentage

import com.katanox.api.charges.percentage.PercentageAppliedOn.FIRST_NIGHT
import com.katanox.api.charges.percentage.PercentageAppliedOn.TOTAL_AMOUNT
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class PercentageChargeServiceTest {

    private val percentageChargeRepository = Mockito.mock(PercentageChargeRepository::class.java)

    private val percentageChargeService = PercentageChargeService(percentageChargeRepository)

    @Test
    fun `test price calculation with no percentage charges`() {
        val hotelId: Long = 1
        val firstNightPrice = BigDecimal.TEN
        val totalAmount = BigDecimal.valueOf(30)
        whenever(percentageChargeRepository.findChargesByHotel(hotelId)).thenReturn(emptySet())

        val result =
            percentageChargeService.calculatePercentageCharges(hotelId, firstNightPrice, totalAmount)

        assertEquals(0.0, result)
    }

    @Test
    fun `test price calculation with all kind of percentage charges`() {
        val hotelId: Long = 1
        val firstNightPrice = BigDecimal.TEN
        val totalAmount = BigDecimal.valueOf(30)
        whenever(percentageChargeRepository.findChargesByHotel(hotelId))
            .thenReturn(getPercentageCharges())

        val result =
            percentageChargeService.calculatePercentageCharges(hotelId, firstNightPrice, totalAmount)

        assertEquals((1.3 * 10) + (1.2 * 30), result)
    }

    private fun getPercentageCharges() = setOf(
        PercentageChargeDto(
            id = 2,
            description = "",
            appliedOn = FIRST_NIGHT,
            percentage = 30.0,
            hotelId = 1
        ),
        PercentageChargeDto(
            id = 1,
            description = "",
            appliedOn = TOTAL_AMOUNT,
            percentage = 20.0,
            hotelId = 1
        )
    )
}
package com.katanox.api.charges

import com.katanox.api.charges.flat.FlatChargeService
import com.katanox.api.charges.percentage.PercentageChargeService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal

internal class ExtraChargeServiceTest {

    private val flatChargeService = Mockito.mock(FlatChargeService::class.java)
    private val percentageChargeService = Mockito.mock(PercentageChargeService::class.java)
    private val extraChargeService = ExtraChargeService(flatChargeService, percentageChargeService)

    @Test
    fun `calculateCharges sums flat and percentage charges`() {
        val numberOfNights = 3
        val firstNightPrice = BigDecimal.valueOf(10)
        val roomPrice = BigDecimal.valueOf(40)
        val hotelId: Long = 1
        whenever(flatChargeService.calculateFlatCharges(hotelId, numberOfNights))
            .thenReturn(20.0)
        whenever(percentageChargeService.calculatePercentageCharges(hotelId, firstNightPrice, roomPrice))
            .thenReturn(30.0)

        val result = extraChargeService.calculateCharges(numberOfNights, firstNightPrice, roomPrice, hotelId)

        assertEquals(BigDecimal.valueOf(50.0), result)
    }
}
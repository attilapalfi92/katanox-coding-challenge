package com.katanox.api.charges.flat

import com.katanox.api.charges.flat.FlatChargeType.ONCE
import com.katanox.api.charges.flat.FlatChargeType.PER_NIGHT
import com.katanox.api.currency.Currency.EUR
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

internal class FlatChargeServiceTest {

    private val flatChargeRepository = Mockito.mock(FlatChargeRepository::class.java)
    private val flatChargeService = FlatChargeService(flatChargeRepository)

    @Test
    fun `test price calculation with no flat charges`() {
        val hotelId: Long = 1
        val numberOfNights = 3
        whenever(flatChargeRepository.findChargesByHotel(hotelId)).thenReturn(emptySet())

        val result =
            flatChargeService.calculateFlatCharges(hotelId, numberOfNights)

        kotlin.test.assertEquals(0.0, result)
    }

    @Test
    fun `test price calculation with all kind of flat charges`() {
        val hotelId: Long = 1
        val numberOfNights = 3
        whenever(flatChargeRepository.findChargesByHotel(hotelId))
            .thenReturn(getFlatCharges())

        val result =
            flatChargeService.calculateFlatCharges(hotelId, numberOfNights)

        kotlin.test.assertEquals(20.0 + (3 * 10), result)
    }

    private fun getFlatCharges(): Set<FlatChargeDto> {
        return setOf(
            FlatChargeDto(
                id = 1,
                description = "",
                chargeType = ONCE,
                price = 20.0,
                currency = EUR,
                hotelId = 1
            ),
            FlatChargeDto(
                id = 2,
                description = "",
                chargeType = PER_NIGHT,
                price = 10.0,
                currency = EUR,
                hotelId = 1
            )
        )
    }
}
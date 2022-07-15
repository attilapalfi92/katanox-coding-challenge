package com.katanox.api.search

import com.katanox.api.charges.ExtraChargeService
import com.katanox.api.prices.PriceDto
import com.katanox.api.prices.PriceService
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate
import java.util.TreeSet
import kotlin.test.assertEquals

class SearchServiceTest {

    private val searchValidator = Mockito.mock(SearchValidator::class.java)
    private val priceService = Mockito.mock(PriceService::class.java)
    private val extraChargeService = Mockito.mock(ExtraChargeService::class.java)

    private val searchService = SearchService(searchValidator, priceService, extraChargeService)

    @Test
    fun `test prices are correctly calculated`() {
        val checkin = LocalDate.of(2022, 7, 11)
        val checkout = LocalDate.of(2022, 7, 13)
        val hotelId: Long = 1
        val request = SearchRequest(
            checkin = checkin,
            checkout = checkout,
            hotelId = hotelId
        )
        whenever(searchValidator.validateSearchRequest(request)).doAnswer {}
        whenever(priceService.findPricesByRoomForDatesInHotel(checkin, checkout, hotelId))
            .thenReturn(
                mapOf(
                    Pair(1, sortedSetOf(priceDto(1), priceDto(2))),
                    Pair(2, sortedSetOf(priceDto(3), priceDto(4)))
                )
            )

        val result = searchService.search(request)

        assertEquals(2, result.rooms.size)
        val resultPrices = result.rooms.map { it.price.toInt() }.sorted().toTypedArray()
        val expectedPrices = listOf(3, 7).toTypedArray()
        assertArrayEquals(expectedPrices, resultPrices)
    }

    private fun priceDto(price: Long): PriceDto {
        return PriceDto(
            date = LocalDate.now(),
            quantity = 1,
            roomId = 1,
            priceBeforeTax = BigDecimal.valueOf(price),
            currency = "EUR"
        )
    }

}
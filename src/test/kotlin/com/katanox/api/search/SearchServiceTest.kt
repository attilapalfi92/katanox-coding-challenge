package com.katanox.api.search

import com.katanox.api.charges.ExtraChargeService
import com.katanox.api.prices.PriceDto
import com.katanox.api.prices.PriceService
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class SearchServiceTest {

    private val searchValidator = Mockito.mock(SearchValidator::class.java)
    private val priceService = Mockito.mock(PriceService::class.java)
    private val extraChargeService = Mockito.mock(ExtraChargeService::class.java)

    private val searchService = SearchService(searchValidator, priceService, extraChargeService)

    @Test
    fun `test prices are correctly calculated when there are no extra charges`() {
        val request = initMocksAndSearchRequest()
        whenever(extraChargeService.calculateCharges(any(), any(), any(), any()))
            .thenReturn(BigDecimal.ZERO)

        val result = searchService.search(request)

        assertEquals(2, result.rooms.size)
        val resultPrices = result.rooms.map { it.priceBeforeTax.toInt() }.sorted().toTypedArray()
        val expectedPrices = listOf(3, 7).toTypedArray()
        assertArrayEquals(expectedPrices, resultPrices)
    }

    @Test
    fun `test prices are correctly calculated when there is extra charge`() {
        val request = initMocksAndSearchRequest()
        whenever(extraChargeService.calculateCharges(any(), any(), any(), any()))
            .thenReturn(BigDecimal.valueOf(3), BigDecimal.valueOf(4))

        val result = searchService.search(request)

        assertEquals(2, result.rooms.size)
        val resultPrices = result.rooms.map { it.priceBeforeTax.toInt() }.sorted().toTypedArray()
        val expectedPrices = listOf(6, 11).toTypedArray()
        assertArrayEquals(expectedPrices, resultPrices)
    }

    private fun initMocksAndSearchRequest(): SearchRequest {
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
            .thenReturn(getRoomsToPricesMap())
        return request
    }

    private fun getRoomsToPricesMap(): Map<Long, TreeSet<PriceDto>> =
        mapOf(
            Pair(
                1,
                sortedSetOf(
                    priceDto(1, 1, LocalDate.of(2022, 7, 11)),
                    priceDto(1, 2, LocalDate.of(2022, 7, 12))
                )
            ),
            Pair(
                2,
                sortedSetOf(
                    priceDto(2, 3, LocalDate.of(2022, 7, 11)),
                    priceDto(2, 4, LocalDate.of(2022, 7, 12))
                )
            )
        )

    private fun priceDto(id: Long, price: Long, date: LocalDate): PriceDto {
        return PriceDto(
            date = date,
            quantity = 1,
            roomId = id,
            priceBeforeTax = BigDecimal.valueOf(price),
            priceAfterTax = BigDecimal.valueOf(1.2 * price),
            currency = "EUR"
        )
    }

}
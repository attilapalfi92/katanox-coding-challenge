package com.katanox.api.search

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.math.BigDecimal
import java.time.LocalDate

/**
 * VAT: 20%
 * room 1:
 *      room prices: 100+110+110+100+130 = 550
 *      room prices with tax: 550 * 1.2 = 660
 *
 *      flat charges: 5*5 + 25 = 50
 *      percentage charges: 0.1*100 + 0.15*550 = 92.5
 *
 *      total priceBeforeTax: 550 + 50 + 92.5 = 692.5
 *      total priceAfterTax: 660 + 50 + 92.5 = 802.5
 *
 * room 2:
 *      room prices: 100+150+200+100+110 = 660
 *      room prices with tax: 660 * 1.2 = 792
 *
 *      flat charges: 5*5 + 25 = 50
 *      percentage charges: 0.1*100 + 0.15*660 = 109
 *
 *      total priceBeforeTax: 660 + 50 + 109 = 819.0
 *      total priceAfterTax: 792 + 50 + 109 = 951.0
 */
@SpringBootTest
@TestPropertySource(locations = ["classpath:test.properties"])
// If the tests were modifying the data:
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SearchServiceIT {

    @Autowired
    private lateinit var searchService: SearchService

    @Test
    fun `test search returns correct before tax prices`() {
        val request = SearchRequest(
            checkin = LocalDate.of(2022, 3, 1),
            checkout = LocalDate.of(2022, 3, 6),
            hotelId = 1
        )

        val result = searchService.search(request)

        assertEquals(
            BigDecimal.valueOf(692.5),
            result.rooms.first { it.roomId == 1L }.priceBeforeTax.stripTrailingZeros()
        )

        assertEquals(
            BigDecimal.valueOf(819),
            result.rooms.first { it.roomId == 2L }.priceBeforeTax.stripTrailingZeros()
        )
    }

    @Test
    fun `test search returns correct after tax prices`() {
        val request = SearchRequest(
            checkin = LocalDate.of(2022, 3, 1),
            checkout = LocalDate.of(2022, 3, 6),
            hotelId = 1
        )

        val result = searchService.search(request)

        assertEquals(
            BigDecimal.valueOf(802.5),
            result.rooms.first { it.roomId == 1L }.priceAfterTax.stripTrailingZeros()
        )

        assertEquals(
            BigDecimal.valueOf(951),
            result.rooms.first { it.roomId == 2L }.priceAfterTax.stripTrailingZeros()
        )
    }

    @Test
    fun `test search with no matching room for dates`() {
        val request = SearchRequest(
            checkin = LocalDate.of(2022, 3, 1),
            checkout = LocalDate.of(2022, 3, 7),
            hotelId = 1
        )

        val result = searchService.search(request)

        assertTrue(result.rooms.isEmpty())
    }
}
package com.katanox.api.search

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate

@SpringBootTest
@TestPropertySource(locations = ["classpath:test.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SearchServiceIT {

    @Autowired
    private lateinit var searchService: SearchService

    @Test
    fun `test search returns correct results`() {
        val request = SearchRequest(
            checkin = LocalDate.of(2022, 3, 1),
            checkout = LocalDate.of(2022, 3, 6),
            hotelId = 1
        )

        val result = searchService.search(request)

        /**
         * room 1:
         *      room prices: 100+110+110+100+130 = 550
         *      flat charges: 5*5 + 25 = 50
         *      percentage charges: 0.1*100 + 0.15*550 = 92.5
         *      total: 692.5
         * room 2:
         *      room prices: 100+150+200+100+110 = 660
         *      flat charges: 5*5 + 25 = 50
         *      percentage charges: 0.1*100 + 0.15*660 = 109
         *      total: 819.0
         */
        println(result)

        // TODO: add proper asserts
    }
}
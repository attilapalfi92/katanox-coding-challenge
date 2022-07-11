package com.katanox.api.prices

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest
@TestPropertySource(locations = ["classpath:test.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PriceServiceIT {

    @Autowired
    private lateinit var underTest: PriceService

    @Test
    fun `test findPricesByRoomForDatesInHotel`() {
        val checkin = LocalDate.of(2022, 3, 1)
        val checkout = LocalDate.of(2022, 3, 4)

        val result = underTest.findPricesByRoomForDatesInHotel(checkin, checkout, 1)

        assertEquals(2, result.size)
        assertEquals(3, result[1]!!.size)
        assertEquals(3, result[2]!!.size)
    }

}
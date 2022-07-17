package com.katanox.api.booking

import com.katanox.api.currency.Currency
import com.katanox.api.guest.Guest
import com.katanox.api.payment.Payment
import com.katanox.api.prices.PriceRepository
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertTrue

@SpringBootTest
@TestPropertySource(locations = ["classpath:test.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookingServiceIT {

    @Autowired
    private lateinit var bookingService: BookingService

    @Autowired
    private lateinit var priceRepository: PriceRepository

    @Autowired
    private lateinit var flyway: Flyway

    private val hotelId: Long = 1

    @AfterEach
    fun tearDown() {
        flyway.clean()
    }

    @Test
    fun `test add booking saves booking, then throws exception when no room is available anymore`() {
        for (i in 0 until 2) {
            bookingService.addBooking(bookingRequest())
        }
        Assertions.assertThrows(ResponseStatusException::class.java) {
            bookingService.addBooking(bookingRequest())
        }

        assertTrue(
            priceRepository.findPriceRecordsByDatesAndRoom(
                setOf(LocalDate.of(2022, 3, 1), LocalDate.of(2022, 3, 2)),
                hotelId
            ).isEmpty(),
            "Rooms should not be available anymore"
        )
    }

    private fun bookingRequest() = BookingRequest(
        hotelId = hotelId,
        roomId = hotelId,
        priceBeforeTax = BigDecimal.ONE,
        priceAfterTax = BigDecimal.ONE,
        currency = Currency.EUR,
        checkin = LocalDate.of(2022, 3, 1),
        checkout = LocalDate.of(2022, 3, 3),
        guest = getGuest(),
        payment = getPayment()
    )

    private fun getGuest() = Guest(hotelId, "name", "name", LocalDate.now())

    private fun getPayment() = Payment(hotelId, "cardHolder", "cardNumber", "cvv", "01", "2022")
}
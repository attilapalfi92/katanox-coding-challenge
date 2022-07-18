package com.katanox.api.booking

import com.katanox.test.sql.tables.Bookings.BOOKINGS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookingRepository(private val dsl: DSLContext) {

    fun insertBooking(bookingDto: BookingDto): Long {
        return dsl.insertInto(
            BOOKINGS,
            BOOKINGS.HOTEL_ID,
            BOOKINGS.ROOM_ID,
            BOOKINGS.GUEST_ID,
            BOOKINGS.PAYMENT_ID,
            BOOKINGS.PRICE_BEFORE_TAX,
            BOOKINGS.PRICE_AFTER_TAX,
            BOOKINGS.CURRENCY
        ).values(
            bookingDto.hotelId,
            bookingDto.roomId,
            bookingDto.guest.id,
            bookingDto.payment.id,
            bookingDto.priceBeforeTax,
            bookingDto.priceAfterTax,
            bookingDto.currency.name
        ).returningResult(BOOKINGS.ID)
            .fetch()[0].value1()
    }
}
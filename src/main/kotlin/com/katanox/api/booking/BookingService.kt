package com.katanox.api.booking

import com.katanox.api.messaging.RabbitMqMessageSenderService
import com.katanox.api.prices.PriceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookingService(
    private val priceService: PriceService,
    private val rabbitMQMessageSenderService: RabbitMqMessageSenderService,
    private val repository: BookingRepository
) {

    @Transactional
    fun addBooking(request: BookingRequest): Long {
        priceService.decreaseQuantities(request.roomId, request.checkin, request.checkout)
        val bookingDto = bookingRequestToDto(request)
        val bookingId = repository.insertBooking(bookingDto)
        rabbitMQMessageSenderService.sendMessage(bookingDto)
        return bookingId
    }

    private fun bookingRequestToDto(request: BookingRequest): BookingDto {
        return BookingDto(
            hotelId = request.hotelId,
            roomId = request.roomId,
            priceBeforeTax = request.priceBeforeTax,
            priceAfterTax = request.priceAfterTax,
            currency = request.currency,
            guest = request.guest,
            payment = request.payment
        )
    }
}
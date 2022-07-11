package com.katanox.api.booking

import com.katanox.api.messaging.RabbitMqMessageSenderService
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookingService(
    private val rabbitMQMessageSenderService: RabbitMqMessageSenderService
) {
    fun booking(bookingRequest: BookingRequest?): Int {
        // persist booking and get id
        val bookingId = Random().nextInt()
        //Map to BookingDTO
        TODO("finish")
//        val bookingDTO = new BookingDto();
//        rabbitMQMessageSenderService.sendMessage(bookingDTO);
//        return bookingId
    }
}
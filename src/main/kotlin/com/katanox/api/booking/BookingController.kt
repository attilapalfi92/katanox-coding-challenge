package com.katanox.api.booking

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("booking")
class BookingController(
    private val bookingService: BookingService
) {

    @PostMapping(
        path = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun booking(@RequestBody request: BookingRequest): ResponseEntity<BookingResponse> {
        val result = bookingService.addBooking(request)
        return ResponseEntity(BookingResponse(result), HttpStatus.ACCEPTED)
    }
}
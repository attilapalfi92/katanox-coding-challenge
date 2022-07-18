package com.katanox.api.search

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class SearchValidator {

    fun validateSearchRequest(request: SearchRequest) {
        if (request.checkout.isBefore(request.checkin.plusDays(1))) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Checkin date must be at least 1 day before checkout date.")
        }
    }
}
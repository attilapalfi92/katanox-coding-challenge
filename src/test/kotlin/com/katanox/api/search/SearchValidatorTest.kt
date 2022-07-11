package com.katanox.api.search

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

class SearchValidatorTest {

    private val searchValidator = SearchValidator()

    @Test
    fun `test with valid input`() {
        assertDoesNotThrow {
            searchValidator.validateSearchRequest(
                SearchRequest(
                    checkin = LocalDate.of(2022, 7, 11),
                    checkout = LocalDate.of(2022, 7, 12),
                    1
                )
            )
        }
    }

    @Test
    fun `test equal dates does throw exception`() {
        assertThrows(ResponseStatusException::class.java) {
            searchValidator.validateSearchRequest(
                SearchRequest(
                    checkin = LocalDate.of(2022, 7, 11),
                    checkout = LocalDate.of(2022, 7, 11),
                    1
                )
            )
        }
    }

    @Test
    fun `test with invalid input`() {
        assertThrows(ResponseStatusException::class.java) {
            searchValidator.validateSearchRequest(
                SearchRequest(
                    checkin = LocalDate.of(2022, 7, 12),
                    checkout = LocalDate.of(2022, 7, 11),
                    1
                )
            )
        }
    }
}
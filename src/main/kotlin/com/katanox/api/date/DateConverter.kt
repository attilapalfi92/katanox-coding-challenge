package com.katanox.api.date

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DateConverter {

    fun intervalToDatesOfNights(
        checkin: LocalDate,
        checkout: LocalDate
    ): HashSet<LocalDate> {
        val dates = HashSet<LocalDate>()
        var dateIterator: LocalDate = checkin
        while (dateIterator.isBefore(checkout)) {
            dates.add(dateIterator)
            dateIterator = dateIterator.plusDays(1)
        }
        return dates
    }
}
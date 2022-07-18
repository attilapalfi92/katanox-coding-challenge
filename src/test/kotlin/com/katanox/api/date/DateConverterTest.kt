package com.katanox.api.date

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DateConverterTest {

    private val dateConverter = DateConverter()

    @Test
    fun `test basic conversion`() {
        val checkin = LocalDate.of(2022, 7, 11)
        val checkout = LocalDate.of(2022, 7, 14)
        val expected = listOf(
            LocalDate.of(2022, 7, 11),
            LocalDate.of(2022, 7, 12),
            LocalDate.of(2022, 7, 13)
        )

        val result = dateConverter.intervalToDatesOfNights(checkin, checkout)

        assertArrayEquals(expected.sorted().toTypedArray(), result.sorted().toTypedArray())
    }

    @Test
    fun `test conversion with same checkin & checkout`() {
        val checkin = LocalDate.of(2022, 7, 11)
        val checkout = LocalDate.of(2022, 7, 12)
        val expected = listOf(
            LocalDate.of(2022, 7, 11)
        )

        val result = dateConverter.intervalToDatesOfNights(checkin, checkout)

        assertArrayEquals(expected.sorted().toTypedArray(), result.sorted().toTypedArray())
    }

    @Test
    fun `test conversion with checkout earlier than checkin`() {
        val checkin = LocalDate.of(2022, 7, 13)
        val checkout = LocalDate.of(2022, 7, 11)
        val expected = emptyList<LocalDate>()

        val result = dateConverter.intervalToDatesOfNights(checkin, checkout)

        assertArrayEquals(expected.sorted().toTypedArray(), result.sorted().toTypedArray())
    }

    @Test
    fun `test conversion with checkout on same day as checkin`() {
        val checkin = LocalDate.of(2022, 7, 11)
        val checkout = LocalDate.of(2022, 7, 11)
        val expected = emptyList<LocalDate>()

        val result = dateConverter.intervalToDatesOfNights(checkin, checkout)

        assertArrayEquals(expected.sorted().toTypedArray(), result.sorted().toTypedArray())
    }
}
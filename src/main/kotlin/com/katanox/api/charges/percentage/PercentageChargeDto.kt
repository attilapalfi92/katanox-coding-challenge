package com.katanox.api.charges.percentage

import com.katanox.test.sql.enums.AppliedOn
import com.katanox.test.sql.tables.ExtraChargesPercentage.EXTRA_CHARGES_PERCENTAGE
import org.jooq.Record

data class PercentageChargeDto(
    val id: Long,
    val description: String,
    val appliedOn: PercentageAppliedOn,
    val percentage: Double,
    val hotelId: Long
) {
    companion object {
        fun ofRecord(record: Record) = PercentageChargeDto(
            id = record.getValue(EXTRA_CHARGES_PERCENTAGE.ID),
            description = record.getValue(EXTRA_CHARGES_PERCENTAGE.DESCRIPTION),
            appliedOn = PercentageAppliedOn.ofAppliedOn(record.getValue(EXTRA_CHARGES_PERCENTAGE.APPLIED_ON)),
            percentage = record.getValue(EXTRA_CHARGES_PERCENTAGE.PERCENTAGE),
            hotelId = record.getValue(EXTRA_CHARGES_PERCENTAGE.HOTEL_ID)
        )
    }
}

enum class PercentageAppliedOn {
    FIRST_NIGHT, TOTAL_AMOUNT;

    companion object {
        fun ofAppliedOn(appliedOn: AppliedOn) = when (appliedOn) {
            AppliedOn.first_night -> FIRST_NIGHT
            AppliedOn.total_amount -> TOTAL_AMOUNT
        }
    }
}

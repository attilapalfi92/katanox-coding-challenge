package com.katanox.api.charges.flat

import com.katanox.api.currency.Currency
import com.katanox.test.sql.enums.ChargeType
import com.katanox.test.sql.tables.ExtraChargesFlat.EXTRA_CHARGES_FLAT
import org.jooq.Record

data class FlatChargeDto(
    val id: Long,
    val description: String,
    val chargeType: FlatChargeType,
    val price: Double,
    val currency: Currency,
    val hotelId: Long
) {
    companion object {
        fun ofRecord(record: Record) = FlatChargeDto(
            id = record.getValue(EXTRA_CHARGES_FLAT.ID),
            description = record.getValue(EXTRA_CHARGES_FLAT.DESCRIPTION),
            chargeType = FlatChargeType.ofChargeType(record.getValue(EXTRA_CHARGES_FLAT.CHARGE_TYPE)),
            price = record.getValue(EXTRA_CHARGES_FLAT.PRICE),
            currency = Currency.valueOf(record.getValue(EXTRA_CHARGES_FLAT.CURRENCY)),
            hotelId = record.getValue(EXTRA_CHARGES_FLAT.HOTEL_ID)
        )
    }
}

enum class FlatChargeType {
    ONCE, PER_NIGHT;

    companion object {
        fun ofChargeType(chargeType: ChargeType) = when (chargeType) {
            ChargeType.per_night -> PER_NIGHT
            ChargeType.once -> ONCE
        }
    }
}
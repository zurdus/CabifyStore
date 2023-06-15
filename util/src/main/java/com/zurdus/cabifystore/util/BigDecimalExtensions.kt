package com.zurdus.cabifystore.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun BigDecimal.formatToEuros(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.FRANCE)
    format.currency = Currency.getInstance("EUR")
    return format.format(this)
}

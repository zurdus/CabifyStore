package com.zurdus.cabifystore.model

import java.math.BigDecimal

abstract class Discount(
    val name: String,
    val amount: BigDecimal,
)

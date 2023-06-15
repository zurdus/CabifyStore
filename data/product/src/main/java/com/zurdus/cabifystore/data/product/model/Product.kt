package com.zurdus.cabifystore.data.product.model

import java.math.BigDecimal

data class Product(
    val code: String,
    val name: String,
    val price: BigDecimal,
    val imageUrl: String,
)

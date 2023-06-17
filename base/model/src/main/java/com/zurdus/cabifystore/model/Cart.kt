package com.zurdus.cabifystore.model

import java.math.BigDecimal

data class Cart(
    val items: List<CartItem>,
) {
    val itemCount: Int
        get() = items.sumOf { it.count }

    val subtotal: BigDecimal
        get() = items.sumOf { it.subtotal }

    val totalDiscount: BigDecimal
        get() = items.sumOf { item -> item.discount }

    val total = items.sumOf { it.total }
}

data class CartItem(
    val product: Product,
    val count: Int,
    val subtotal: BigDecimal,
    val discount: BigDecimal,
    val total: BigDecimal,
)

package com.zurdus.cabifystore.model

import java.math.BigDecimal

data class Cart(
    val items: Set<CartItem>,
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
    val discount: BigDecimal,
) {
    val subtotal: BigDecimal
        get() = product.price.times(BigDecimal(count))

    val total: BigDecimal
        get() = subtotal - discount
}

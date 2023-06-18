package com.zurdus.cabifystore.feature.checkout.ui

import com.zurdus.cabifystore.model.CartItem
import java.math.BigDecimal

internal data class CartItemVisual(
    val imageUrl: String,
    val name: String,
    val itemCount: Int,
    val subtotal: BigDecimal,
    val discount: BigDecimal,
    val total: BigDecimal,
)

internal fun CartItem.getVisual(): CartItemVisual {
    return CartItemVisual(
        imageUrl = product.imageUrl,
        name = product.name,
        itemCount = count,
        subtotal = subtotal,
        discount = discount,
        total = total,
    )
}

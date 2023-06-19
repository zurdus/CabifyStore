package com.zurdus.cabifystore.data.discount.impl.model

import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Discount
import java.math.BigDecimal

internal class TwoForOneDiscount(
    applicableProductCodes: Set<String>,
) : Discount(applicableProductCodes) {

    override fun isApplicable(cartItem: CartItem): Boolean = cartItem.count >= 2

    override fun apply(cartItem: CartItem): CartItem {
        val pairs = cartItem.count / 2
        val discount = cartItem.product.price * BigDecimal(pairs)
        return cartItem.copy(discount = cartItem.discount + discount)
    }
}

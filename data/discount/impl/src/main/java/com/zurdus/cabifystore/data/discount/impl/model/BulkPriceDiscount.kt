package com.zurdus.cabifystore.data.discount.impl.model

import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Discount
import java.math.BigDecimal

class BulkPriceDiscount(
    applicableProductCodes: Set<String>,
    private val bulkPrice: BigDecimal,
) : Discount(applicableProductCodes) {

    override fun isApplicable(cartItem: CartItem): Boolean = cartItem.count >= 3

    override fun apply(cartItem: CartItem): CartItem {
        val regularPrice = cartItem.product.price

        require(bulkPrice < cartItem.product.price) {
            "Discounted price $bulkPrice cannot be higher than regular price $regularPrice."
        }

        val discountPerItem = regularPrice - bulkPrice
        val discount = discountPerItem * BigDecimal(cartItem.count)
        return cartItem.copy(discount = cartItem.discount + discount)
    }
}

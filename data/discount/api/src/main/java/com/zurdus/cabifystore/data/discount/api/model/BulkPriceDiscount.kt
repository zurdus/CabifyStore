package com.zurdus.cabifystore.data.discount.api.model

import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Discount
import java.math.BigDecimal

class BulkPriceDiscount(
    applicableProductCodes: Set<String>,
    private val itemCountThreshold: Int,
    private val bulkPrice: BigDecimal,
) : Discount(applicableProductCodes) {

    override fun isApplicable(cartItem: CartItem): Boolean = cartItem.count >= itemCountThreshold

    override fun apply(cartItem: CartItem): CartItem {
        val regularPrice = cartItem.product.price

        require(bulkPrice < cartItem.product.price) {
            "Discounted price $bulkPrice cannot be higher than regular price $regularPrice."
        }

        val discountPerItem = regularPrice - bulkPrice
        val discount = discountPerItem * BigDecimal(cartItem.count)
        val item = cartItem.copy(discount = cartItem.discount + discount)
        return item
    }
}

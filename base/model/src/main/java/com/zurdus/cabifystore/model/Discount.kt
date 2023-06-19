package com.zurdus.cabifystore.model

abstract class Discount(
    private val applicableProductCodes: Set<String>,
) {
    abstract fun isApplicable(cartItem: CartItem): Boolean
    abstract fun apply(cartItem: CartItem): CartItem

    fun evaluate(cartItem: CartItem): CartItem =
        if (cartItem.product.code in applicableProductCodes && isApplicable(cartItem)) {
            apply(cartItem)
        } else {
            cartItem
        }
}

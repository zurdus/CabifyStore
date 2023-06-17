package com.zurdus.cabifystore.domain.checkout.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.model.Cart
import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class LoadCart(
    private val cartSource: CartSource,
    private val discountRepository: DiscountRepository,
) {
    operator fun invoke(): Flow<Cart> {
        return cartSource
            .getProducts()
            .map { createCartItems(it) }
            .map { cartItems ->
                Cart(items = cartItems)
            }
    }

    private fun createCartItems(products: List<Product>): List<CartItem> =
        products
            .groupBy {
                it
            }
            .map { entry ->
                createCartItem(entry.key, entry.value.size)
            }

    private fun createCartItem(product: Product, itemCount: Int): CartItem {
        val subtotal = product.price.times(BigDecimal(itemCount))
        val discount = BigDecimal(0)

        return CartItem(
            product = product,
            count = itemCount,
            subtotal = subtotal,
            discount = discount,
            total = subtotal - discount,
        )
    }
}

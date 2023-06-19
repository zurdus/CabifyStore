package com.zurdus.cabifystore.domain.checkout.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.model.Cart
import com.zurdus.cabifystore.model.CartItem
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import java.math.BigDecimal

class LoadCart(
    private val cartSource: CartSource,
    private val discountRepository: DiscountRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Cart> {
        return cartSource
            .getProducts()
            .mapLatest { products ->
                val initialItems: Set<CartItem> = createCartItems(products)

                val discounts = discountRepository.getDiscounts()
                val discountedItems: Set<CartItem> = initialItems.map { item ->
                    discounts.fold(item) { cartItem, discount ->
                        discount.evaluate(cartItem)
                    }
                }.toSet()

                Cart(items = discountedItems)
            }
    }

    private fun createCartItems(products: List<Product>): Set<CartItem> =
        products
            .groupBy {
                it
            }
            .map { entry ->
                createCartItem(entry.key, entry.value.size)
            }
            .toSet()

    private fun createCartItem(product: Product, itemCount: Int): CartItem {
        val discount = BigDecimal(0)

        return CartItem(
            product = product,
            count = itemCount,
            discount = discount,
        )
    }
}

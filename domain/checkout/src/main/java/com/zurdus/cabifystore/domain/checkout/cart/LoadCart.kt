package com.zurdus.cabifystore.domain.checkout.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.flow.Flow

class LoadCart(
    private val cartSource: CartSource,
) {
    operator fun invoke(): Flow<List<Product>> = cartSource.getProducts()
}

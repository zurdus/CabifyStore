package com.zurdus.cabifystore.domain.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.data.product.api.model.Product
import kotlinx.coroutines.flow.Flow

class LoadCart(
    private val cartSource: CartSource,
) {
    operator fun invoke(): Flow<List<Product>> = cartSource.getProducts()
}

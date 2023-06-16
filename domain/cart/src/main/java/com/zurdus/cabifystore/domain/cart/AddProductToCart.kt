package com.zurdus.cabifystore.domain.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.model.Product
import com.zurdus.cabifystore.util.Dispatchers
import kotlinx.coroutines.withContext

class AddProductToCart(
    private val dispatchers: Dispatchers,
    private val cart: CartSource,
) {
    suspend operator fun invoke(product: Product) = withContext(dispatchers.IO) {
        cart.addProduct(product)
    }
}

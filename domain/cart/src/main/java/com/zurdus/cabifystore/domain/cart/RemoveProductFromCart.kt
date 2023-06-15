package com.zurdus.cabifystore.domain.cart

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.util.Dispatchers
import com.zurdus.data.product.api.model.Product
import kotlinx.coroutines.withContext

class RemoveProductFromCart(
    private val cartSource: CartSource,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(product: Product) = withContext(dispatchers.IO) {
        cartSource.removeProduct(product)
    }
}

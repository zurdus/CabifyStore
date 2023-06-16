package com.zurdus.cabifystore.domain.product

import com.zurdus.cabifystore.util.Dispatchers
import com.zurdus.data.product.api.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoadProducts(
    private val productRepository: ProductRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke() = withContext(dispatchers.IO) {
        productRepository.getProducts()
    }
}

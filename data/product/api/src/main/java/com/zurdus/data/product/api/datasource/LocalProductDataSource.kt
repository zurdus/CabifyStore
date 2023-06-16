package com.zurdus.data.product.api.datasource

import com.zurdus.cabifystore.model.Product

interface LocalProductDataSource : ProductDataSource {
    suspend fun saveProducts(products: List<Product>)
}

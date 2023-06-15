package com.zurdus.data.product.api.datasource

import com.zurdus.data.product.api.model.Product

interface LocalProductDataSource : ProductDataSource {
    suspend fun saveProducts(products: List<Product>)
}

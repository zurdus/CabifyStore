package com.zurdus.data.product.impl

import com.zurdus.cabifystore.common.response.Response
import com.zurdus.cabifystore.model.Product
import com.zurdus.data.product.api.ProductRepository
import com.zurdus.data.product.api.datasource.LocalProductDataSource
import com.zurdus.data.product.api.datasource.ProductDataSource

internal class DefaultProductRepository(
    private val remoteSource: ProductDataSource,
    private val localSource: LocalProductDataSource,
) : ProductRepository {

    override suspend fun getProducts(): Response<List<Product>> {
        val remoteProducts = remoteSource.getProducts()

        val productsResponse = if (remoteProducts is Response.Success) {
            localSource.saveProducts(remoteProducts.data)
            remoteProducts
        } else {
            localSource.getProducts()
        }

        return productsResponse
    }
}

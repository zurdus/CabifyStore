package com.zurdus.cabifystore.data.product

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.cabifystore.data.product.datasource.LocalProductDataSource
import com.zurdus.cabifystore.data.product.datasource.ProductImageDataSource
import com.zurdus.cabifystore.data.product.datasource.RemoteProductDataSource
import com.zurdus.cabifystore.data.product.model.Product
import java.math.BigDecimal

internal class DefaultProductRepository(
    private val remoteSource: RemoteProductDataSource,
    private val localSource: LocalProductDataSource,
    private val imageDataSource: ProductImageDataSource,
) : ProductRepository {

    override suspend fun getProducts(): Response<List<Product>> =
        try {
            val remoteProducts: List<Product> = remoteSource.getProducts().products
                .map { product ->
                    val productImageUrl = imageDataSource.getProductImageUrl(product.code)

                    Product(
                        code = product.code,
                        name = product.name,
                        price = BigDecimal(product.price),
                        imageUrl = productImageUrl
                    )
                }

            localSource.saveProducts(remoteProducts)

            Response.Success(remoteProducts)
        } catch (e: Exception) {
            loadLocalProducts()
        }

    private fun loadLocalProducts(): Response<List<Product>> = localSource.getProducts()
}

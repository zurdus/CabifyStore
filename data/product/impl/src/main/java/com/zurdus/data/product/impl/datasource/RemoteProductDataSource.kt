package com.zurdus.data.product.impl.datasource

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.cabifystore.base.response.ResponseError
import com.zurdus.data.product.api.datasource.ProductDataSource
import com.zurdus.data.product.api.model.Product
import com.zurdus.data.product.impl.datasource.framework.ProductApi
import java.math.BigDecimal

internal class RemoteProductDataSource(
    private val productApi: ProductApi,
    private val imageDataSource: ProductImageDataSource,
) : ProductDataSource {
    override suspend fun getProducts(): Response<List<Product>> =
        try {
            val remoteProducts: List<Product> = productApi.getProducts().products
                .map { product ->
                    val productImageUrl = imageDataSource.getProductImageUrl(product.code)

                    Product(
                        code = product.code,
                        name = product.name,
                        price = BigDecimal(product.price),
                        imageUrl = productImageUrl
                    )
                }

            Response.Success(remoteProducts)
        } catch (e: Exception) {
            Response.Failure(ResponseError.Network)
        }
}

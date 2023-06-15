package com.zurdus.data.product.impl.datasource.framework

import kotlinx.serialization.Serializable
import retrofit2.http.GET

internal interface ProductApi {
    @GET("Products.json")
    suspend fun getProducts(): ProductsResponse
}

@Serializable
internal data class ProductsResponse(val products: List<RemoteProduct>)

@Serializable
internal data class RemoteProduct(val code: String, val name: String, val price: Double)

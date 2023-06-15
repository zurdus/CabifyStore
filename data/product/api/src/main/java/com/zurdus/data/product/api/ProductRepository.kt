package com.zurdus.data.product.api

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.data.product.api.model.Product

interface ProductRepository {
    suspend fun getProducts(): Response<List<Product>>
}

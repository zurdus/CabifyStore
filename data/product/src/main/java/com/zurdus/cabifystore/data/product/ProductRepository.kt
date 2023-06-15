package com.zurdus.cabifystore.data.product

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.cabifystore.data.product.model.Product

interface ProductRepository {
    suspend fun getProducts(): Response<List<Product>>
}

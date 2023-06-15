package com.zurdus.data.product.api.datasource

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.data.product.api.model.Product

interface ProductDataSource {
    suspend fun getProducts(): Response<List<Product>>
}

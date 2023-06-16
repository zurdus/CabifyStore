package com.zurdus.cabifystore.data.cart.api

import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.flow.Flow

interface CartSource {

    suspend fun addProduct(product: Product)

    suspend fun removeProduct(product: Product)

    fun getProducts(): Flow<List<Product>>

    fun getProductCount(): Flow<Int>
}

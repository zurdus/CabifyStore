package com.zurdus.cabifystore.data.product.datasource

import com.zurdus.cabifystore.base.response.Response
import com.zurdus.cabifystore.base.response.ResponseError
import com.zurdus.cabifystore.data.product.model.Product


internal class LocalProductDataSource(
    context: Context,
) {

    private var localProducts: List<Product>? = null

    fun getProducts(): Response<List<Product>> {
        val products = localProducts

        return if (products == null) {
            Response.Failure(ResponseError.Unknown)
        } else {
            Response.Success(products)
        }
    }

    fun saveProducts(products: List<Product>) {
        localProducts = products
    }
}

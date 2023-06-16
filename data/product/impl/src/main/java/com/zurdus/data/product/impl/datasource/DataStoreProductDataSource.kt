package com.zurdus.data.product.impl.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zurdus.cabifystore.common.response.Response
import com.zurdus.cabifystore.common.response.ResponseError
import com.zurdus.cabifystore.model.Product
import com.zurdus.data.product.api.datasource.LocalProductDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigDecimal


internal class DataStoreProductDataSource(
    private val context: Context,
) : LocalProductDataSource {

    private val Context.productsDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "products"
    )

    private object PreferencesKeys {
        val PRODUCTS = stringPreferencesKey("products")
    }

    override suspend fun getProducts(): Response<List<Product>> {
        val response = withTimeoutOrNull(5_000) {
            getProductsFromDataStore().first()
        }

        return response ?: Response.Failure(ResponseError.Unknown)
    }

    override suspend fun saveProducts(products: List<Product>) {
        val localProducts = products.map { LocalProduct.fromProduct(it) }

        context.productsDataStore.edit { preferences ->
            preferences[PreferencesKeys.PRODUCTS] = Json.encodeToString(localProducts)
        }
    }

    private fun getProductsFromDataStore(): Flow<Response<List<Product>>> {
        return context.productsDataStore.data.map { preferences ->
            val localProductsString = preferences[PreferencesKeys.PRODUCTS]

            try {
                val localProducts: List<LocalProduct> = localProductsString?.let {
                    Json.decodeFromString(it)
                } ?: emptyList()

                val products = localProducts.map { LocalProduct.toProduct(it) }

                Response.Success(products)
            } catch (e: Exception) {
                Response.Failure(ResponseError.Unknown)
            }
        }
    }
}

@Serializable
internal data class LocalProduct(
    val code: String,
    val name: String,
    val price: String,
    val imageUrl: String
) {
    companion object {
        fun fromProduct(product: Product) =
            LocalProduct(
                code = product.code,
                name = product.name,
                price = product.price.toString(),
                imageUrl = product.imageUrl,
            )

        fun toProduct(localProduct: LocalProduct) =
            Product(
                code = localProduct.code,
                name = localProduct.name,
                price = BigDecimal(localProduct.price),
                imageUrl = localProduct.imageUrl,
            )
    }
}

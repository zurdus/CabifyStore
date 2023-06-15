package com.zurdus.cabifystore.data.cart.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.data.product.api.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class DataStoreCart(
    private val context: Context,
) : CartSource {

    private val Context.cartDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "cart"
    )

    private object PreferencesKeys {
        val CART = stringPreferencesKey("cart")
    }

    override suspend fun addProduct(product: Product) {
        val savedCartProducts = getProducts().first().toMutableList()
        savedCartProducts.add(product)

        saveProductList(savedCartProducts)
    }

    override suspend fun removeProduct(product: Product) {
        val savedCartProducts = getProducts().first().toMutableList()
        savedCartProducts.remove(product)

        saveProductList(savedCartProducts)
    }

    override fun getProducts(): Flow<List<Product>> =
        context.cartDataStore.data.map { preferences ->
            val productsString = preferences[PreferencesKeys.CART]

            val localProducts: List<LocalProduct> = productsString?.let {
                Json.decodeFromString(it)
            } ?: emptyList()

            localProducts.map { LocalProduct.toProduct(it) }
        }

    override fun getProductCount(): Flow<Int> = getProducts().map { it.count() }

    private suspend fun saveProductList(products: List<Product>) {
        val localProducts = products.map { LocalProduct.fromProduct(it) }.toList()

        context.cartDataStore.edit { preferences ->
            preferences[PreferencesKeys.CART] = Json.encodeToString(localProducts)
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

package com.zurdus.cabifystore.feature.catalog.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurdus.cabifystore.base.response.ResponseError
import com.zurdus.cabifystore.base.response.doOnFailure
import com.zurdus.cabifystore.base.response.doOnSuccess
import com.zurdus.cabifystore.domain.product.LoadProducts
import com.zurdus.data.product.api.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val loadProducts: LoadProducts,
//    private val getCartCount: GetCartCount,
//    private val addProductToCart: AddProductToCart,
//    private val removeProductFromCart: RemoveProductFromCart,
) : ViewModel() {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products = _products.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _error: MutableStateFlow<ResponseError?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    init {
        loadCatalog(refreshing = false)
    }

    fun onCatalogRefresh() {
        loadCatalog(refreshing = true)
    }

    fun onProductItemClick(product: Product) {
        Log.e("***********", "Clicked on ${product.name}")
    }

    private fun loadCatalog(refreshing: Boolean) {
        if (loading.value) return

        viewModelScope.launch {
            if (refreshing) {
                _refreshing.value = true
            } else {
                _loading.value = true
            }

            loadProducts()
                .doOnSuccess { products ->
                    _products.value = products
                    _error.value = null
                    _loading.value = false
                    _refreshing.value = false
                }
                .doOnFailure { error ->
                    _error.value = error
                    _loading.value = false
                    _refreshing.value = false
                }

        }
    }
}

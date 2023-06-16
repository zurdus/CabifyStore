package com.zurdus.cabifystore.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurdus.cabifystore.common.response.ResponseError
import com.zurdus.cabifystore.common.response.doOnFailure
import com.zurdus.cabifystore.common.response.doOnSuccess
import com.zurdus.cabifystore.domain.cart.AddProductToCart
import com.zurdus.cabifystore.domain.cart.LoadCart
import com.zurdus.cabifystore.domain.cart.RemoveProductFromCart
import com.zurdus.cabifystore.domain.product.LoadProducts
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CatalogViewModel(
    loadCart: LoadCart,
    private val loadProducts: LoadProducts,
    private val addProductToCart: AddProductToCart,
    private val removeProductFromCart: RemoveProductFromCart,
) : ViewModel() {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products = _products.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _error: MutableStateFlow<ResponseError?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    val cartCount: StateFlow<Int> = loadCart()
        .map { products ->
            products.count()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0
        )

    val totalPrice: StateFlow<BigDecimal> = loadCart()
        .map { products ->
            products.sumOf { it.price }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BigDecimal("0")
        )

    init {
        loadCatalog(refreshing = false)
    }

    fun onCatalogRefresh() {
        loadCatalog(refreshing = true)
    }

    fun onProductItemClick(product: Product) {
        viewModelScope.launch {
            addProductToCart(product)
        }
    }

    fun onProductLongClick(product: Product) {
        viewModelScope.launch {
            removeProductFromCart(product)
        }
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

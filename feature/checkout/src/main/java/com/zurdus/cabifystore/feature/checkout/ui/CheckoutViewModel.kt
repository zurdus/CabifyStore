package com.zurdus.cabifystore.feature.checkout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurdus.cabifystore.domain.checkout.cart.AddProductToCart
import com.zurdus.cabifystore.domain.checkout.cart.LoadCart
import com.zurdus.cabifystore.domain.checkout.cart.RemoveProductFromCart
import com.zurdus.cabifystore.model.Cart
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class CheckoutViewModel(
    loadCart: LoadCart,
    private val addProductToCart: AddProductToCart,
    private val removeProductFromCart: RemoveProductFromCart,
) : ViewModel() {

    val cart: StateFlow<Cart> = loadCart().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Cart(setOf()),
    )

    private val _productToDelete: MutableStateFlow<Product?> = MutableStateFlow(null)
    val productToDelete = _productToDelete.asStateFlow()

    fun onProductCountChange(product: Product, newCount: Int) {
        viewModelScope.launch {
            val currentCount = cart.value.items.find { it.product == product }?.count
                ?: return@launch

            if (newCount == 0) {
                _productToDelete.value = product
                return@launch
            }

            if (newCount < currentCount) {
                removeProductFromCart(product)
            } else {
                addProductToCart(product)
            }
        }
    }

    fun onProductDeleteCancel() {
        _productToDelete.value = null
    }

    fun onProductDeleteConfirm(product: Product) {
        viewModelScope.launch {
            removeProductFromCart(product)
        }

        _productToDelete.value = null
    }
}

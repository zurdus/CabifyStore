package com.zurdus.cabifystore.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurdus.cabifystore.domain.checkout.cart.AddProductToCart
import com.zurdus.cabifystore.domain.checkout.cart.RemoveProductFromCart
import com.zurdus.cabifystore.model.Product
import kotlinx.coroutines.launch

class CatalogDetailViewModel(
    private val addProductToCart: AddProductToCart,
    private val removeProductFromCart: RemoveProductFromCart,
) : ViewModel() {

    fun onAddProductClick(product: Product) {
        viewModelScope.launch {
            addProductToCart(product)
        }
    }
}

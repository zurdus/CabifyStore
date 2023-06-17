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

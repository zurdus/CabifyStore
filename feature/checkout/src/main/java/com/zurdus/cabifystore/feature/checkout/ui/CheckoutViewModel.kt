package com.zurdus.cabifystore.feature.checkout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurdus.cabifystore.domain.checkout.cart.LoadCart
import com.zurdus.cabifystore.model.Cart
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

internal class CheckoutViewModel(
    loadCart: LoadCart,
) : ViewModel() {

    val cart: StateFlow<Cart?> = loadCart().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Cart(listOf()),
    )
}

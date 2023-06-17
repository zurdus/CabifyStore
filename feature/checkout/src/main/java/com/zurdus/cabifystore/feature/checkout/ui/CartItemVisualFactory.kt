package com.zurdus.cabifystore.feature.checkout.ui

import com.zurdus.cabifystore.model.Product

internal data class CartItemVisual(
    val imageUrl: String,
    val name: String,
    val itemCount: Int,
    val subtotal: String,
    val discount: String,
    val total: String,
)

//private fun Product.getCartItemVisual(): CartItemVisual {
//    return CartItemVisual(
//        imageUrl = imageUrl,
//        name = name,
//        itemCount = count,
//        subtotal = subtotal,
//        discount = "0",
//        total = subtotal,
//    )
//}

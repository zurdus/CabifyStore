package com.zurdus.cabifystore.domain.checkout.cart.di

import com.zurdus.cabifystore.domain.checkout.cart.AddProductToCart
import com.zurdus.cabifystore.domain.checkout.cart.LoadCart
import com.zurdus.cabifystore.domain.checkout.cart.RemoveProductFromCart
import com.zurdus.cabifystore.domain.checkout.discount.LoadDiscounts
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainCheckoutModule = module {

    factoryOf(::AddProductToCart)
    factoryOf(::RemoveProductFromCart)
    factoryOf(::LoadCart)

    factoryOf(::LoadDiscounts)
}

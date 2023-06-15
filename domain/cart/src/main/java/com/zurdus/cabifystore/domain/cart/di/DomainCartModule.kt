package com.zurdus.cabifystore.domain.cart.di

import com.zurdus.cabifystore.domain.cart.AddProductToCart
import com.zurdus.cabifystore.domain.cart.LoadCart
import com.zurdus.cabifystore.domain.cart.RemoveProductFromCart
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainCartModule = module {

    factoryOf(::AddProductToCart)
    factoryOf(::RemoveProductFromCart)
    factoryOf(::LoadCart)
}

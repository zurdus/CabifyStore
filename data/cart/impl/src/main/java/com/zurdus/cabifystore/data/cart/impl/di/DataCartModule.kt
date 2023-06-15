package com.zurdus.cabifystore.data.cart.impl.di

import com.zurdus.cabifystore.data.cart.api.CartSource
import com.zurdus.cabifystore.data.cart.impl.DataStoreCart
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataCartModule = module {
    singleOf(::DataStoreCart) bind CartSource::class
}

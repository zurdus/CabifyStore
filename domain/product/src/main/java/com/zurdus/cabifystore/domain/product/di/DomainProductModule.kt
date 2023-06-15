package com.zurdus.cabifystore.domain.product.di

import com.zurdus.cabifystore.domain.product.LoadProducts
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainProductModule = module {
    factoryOf(::LoadProducts)
}

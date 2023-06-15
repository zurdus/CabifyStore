package com.zurdus.cabifystore.domain.product.di

import com.zurdus.cabifystore.data.product.di.productDataModule
import com.zurdus.cabifystore.domain.product.LoadProducts
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val productDomainModule = module {
    includes(productDataModule)

    factoryOf(::LoadProducts)
}

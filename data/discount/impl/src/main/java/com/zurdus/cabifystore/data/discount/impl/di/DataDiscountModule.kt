package com.zurdus.cabifystore.data.discount.impl.di

import com.zurdus.cabifystore.data.discount.api.DiscountRepository
import com.zurdus.cabifystore.data.discount.impl.DefaultDiscountRepository
import com.zurdus.cabifystore.data.discount.impl.MemoryDiscountDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataDiscountModule = module {
    singleOf(::DefaultDiscountRepository) bind DiscountRepository::class
    singleOf(::MemoryDiscountDataSource)
}

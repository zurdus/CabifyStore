package com.zurdus.cabifystore.di

import com.zurdus.cabifystore.util.DefaultDispatchers
import com.zurdus.cabifystore.util.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::DefaultDispatchers) bind Dispatchers::class
}

package com.zurdus.cabifystore.feature.catalog.di

import com.zurdus.cabifystore.feature.catalog.ui.CatalogDetailViewModel
import com.zurdus.cabifystore.feature.catalog.ui.CatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureCatalogModule = module {
    viewModelOf(::CatalogViewModel)
    viewModelOf(::CatalogDetailViewModel)
}

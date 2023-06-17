package com.zurdus.cabifystore.feature.checkout.di

import com.zurdus.cabifystore.feature.checkout.ui.CheckoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureCheckoutModule = module {
    viewModelOf(::CheckoutViewModel)
}

package com.zurdus.cabifystore

import android.app.Application
import com.zurdus.cabifystore.data.cart.impl.di.dataCartModule
import com.zurdus.cabifystore.data.discount.impl.di.dataDiscountModule
import com.zurdus.cabifystore.di.appModule
import com.zurdus.cabifystore.domain.checkout.cart.di.domainCheckoutModule
import com.zurdus.cabifystore.domain.product.di.domainProductModule
import com.zurdus.cabifystore.feature.catalog.di.featureCatalogModule
import com.zurdus.data.product.impl.di.dataProductModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(
                appModule,

                featureCatalogModule,

                domainProductModule,
                domainCheckoutModule,

                dataProductModule,
                dataCartModule,
                dataDiscountModule,
            )
        }

    }
}

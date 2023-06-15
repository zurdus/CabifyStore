package com.zurdus.data.product.impl.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zurdus.data.product.api.ProductRepository
import com.zurdus.data.product.api.datasource.LocalProductDataSource
import com.zurdus.data.product.api.datasource.ProductDataSource
import com.zurdus.data.product.impl.DefaultProductRepository
import com.zurdus.data.product.impl.datasource.DataStoreProductDataSource
import com.zurdus.data.product.impl.datasource.ProductImageDataSource
import com.zurdus.data.product.impl.datasource.RemoteProductDataSource
import com.zurdus.data.product.impl.datasource.framework.ProductApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

private const val CATALOG_BASE_URL =
    "https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/"

val dataProductModule = module {
    singleOf(::DefaultProductRepository) bind ProductRepository::class

    single<ProductApi> {
        val contentType = MediaType.get("application/json")
        Retrofit.Builder()
            .baseUrl(CATALOG_BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(ProductApi::class.java)
    }

    singleOf(::DefaultProductRepository) bind ProductRepository::class

    singleOf(::DataStoreProductDataSource) bind LocalProductDataSource::class
    singleOf(::RemoteProductDataSource) bind ProductDataSource::class
    singleOf(::ProductImageDataSource)
}

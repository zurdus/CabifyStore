package com.zurdus.cabifystore.data.product.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zurdus.cabifystore.data.product.DefaultProductRepository
import com.zurdus.cabifystore.data.product.ProductRepository
import com.zurdus.cabifystore.data.product.datasource.LocalProductDataSource
import com.zurdus.cabifystore.data.product.datasource.ProductImageDataSource
import com.zurdus.cabifystore.data.product.datasource.RemoteProductDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

private const val CATALOG_BASE_URL =
    "https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/"

val productDataModule = module {
    singleOf(::DefaultProductRepository) bind ProductRepository::class

    single<RemoteProductDataSource> {
        val contentType = MediaType.get("application/json")
        Retrofit.Builder()
            .baseUrl(CATALOG_BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(RemoteProductDataSource::class.java)
    }

    singleOf(::LocalProductDataSource)

    singleOf(::ProductImageDataSource)
}

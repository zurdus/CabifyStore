package com.zurdus.cabifystore.feature.catalog.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.zurdus.data.product.api.model.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigDecimal

@Parcelize
@Serializable
data class ParcelableProduct(
    val code: String,
    val name: String,
    val price: String,
    val imageUrl: String,
) : Parcelable {
    companion object {
        fun fromProduct(product: Product) =
            ParcelableProduct(
                code = product.code,
                name = product.name,
                price = product.price.toString(),
                imageUrl = product.imageUrl.replace("/", "$$$"),
            )

        fun toProduct(parcelableProduct: ParcelableProduct) =
            Product(
                code = parcelableProduct.code,
                name = parcelableProduct.name,
                price = BigDecimal(parcelableProduct.price),
                imageUrl = parcelableProduct.imageUrl.replace("$$$", "/"),
            )
    }

    override fun toString(): String = Json.encodeToString(this)
}

class ProductType : NavType<ParcelableProduct>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ParcelableProduct? =
        bundle.getParcelable(key)

    override fun parseValue(value: String): ParcelableProduct =
        Json.decodeFromString(value)

    override fun put(bundle: Bundle, key: String, value: ParcelableProduct) {
        bundle.putParcelable(key, value)
    }
}

package com.zurdus.cabifystore.feature.catalog.ui

import androidx.compose.runtime.Composable
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.base.ui.theme.ColorSystem
import com.zurdus.cabifystore.model.Product
import com.zurdus.cabifystore.util.formatToEuros

internal data class CatalogItemVisual(
    val imageUrl: String,
    val name: String,
    val price: String,
    val colorSystem: ColorSystem,
)

@Composable
internal fun Product.getCatalogItemVisual(index: Int) =
    CatalogItemVisual(
        imageUrl = imageUrl,
        name = name,
        price = price.formatToEuros(),
        colorSystem = getColorSystem(index),
    )

@Composable
private fun getColorSystem(index: Int): ColorSystem {
    val colorSystems = listOf(
        CabifyTheme.color.pink,
        CabifyTheme.color.blue,
        CabifyTheme.color.yellow,
        CabifyTheme.color.orange,
        CabifyTheme.color.moradul,
        CabifyTheme.color.green,
        CabifyTheme.color.red,
    )

    val position = index % colorSystems.size

    return colorSystems[position]
}


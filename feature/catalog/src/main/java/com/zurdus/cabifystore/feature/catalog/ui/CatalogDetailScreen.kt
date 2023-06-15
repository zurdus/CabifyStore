package com.zurdus.cabifystore.feature.catalog.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.zurdus.data.product.api.model.Product

@Composable
fun CatalogDetailScreen(product: Product) {
    Text(text = product.toString())
}

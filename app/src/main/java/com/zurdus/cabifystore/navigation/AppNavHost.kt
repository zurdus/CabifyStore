package com.zurdus.cabifystore.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zurdus.cabifystore.feature.cart.ui.CartScreen
import com.zurdus.cabifystore.feature.catalog.navigation.ParcelableProduct
import com.zurdus.cabifystore.feature.catalog.navigation.ProductType
import com.zurdus.cabifystore.feature.catalog.ui.CatalogDetailScreen
import com.zurdus.cabifystore.feature.catalog.ui.CatalogScreen

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "catalog") {
        composable("catalog") { CatalogScreen(navController) }

        dialog(
            route = "catalog/{product}",
            arguments = listOf(
                navArgument(name = "product") { type = ProductType() }
            )
        ) { entry ->
            val parcelableProduct = entry.arguments?.getParcelable<ParcelableProduct>("product")

            parcelableProduct?.let {
                CatalogDetailScreen(ParcelableProduct.toProduct(parcelableProduct))
            }
        }

        dialog(
            route = "test/{hola}",
            arguments = listOf(
                navArgument("hola") { type = NavType.StringType }
            )
        ) { entry ->
            val hola = entry.arguments?.getString("hola") ?: "Not found"

            Text(text = hola)
        }

        composable("cart") { CartScreen(navController) }
    }
}

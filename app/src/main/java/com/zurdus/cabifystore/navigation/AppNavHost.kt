package com.zurdus.cabifystore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zurdus.cabifystore.feature.cart.ui.CartScreen
import com.zurdus.cabifystore.feature.catalog.ui.CatalogScreen

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "catalog") {
        composable("catalog") { CatalogScreen(navController) }

        composable("cart") { CartScreen(navController) }
    }
}

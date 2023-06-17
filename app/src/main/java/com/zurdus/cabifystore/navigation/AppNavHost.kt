package com.zurdus.cabifystore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zurdus.cabifystore.feature.cart.navigation.cartNavGraph
import com.zurdus.cabifystore.feature.catalog.navigation.CATALOG_ROOT
import com.zurdus.cabifystore.feature.catalog.navigation.catalogNavGraph

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CATALOG_ROOT) {
        catalogNavGraph(navController)

        cartNavGraph(navController)
    }
}

package com.zurdus.cabifystore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zurdus.cabifystore.feature.catalog.navigation.catalogNavGraph
import com.zurdus.cabifystore.feature.checkout.navigation.cartNavGraph

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraphRoot.CatalogGraph.name) {
        catalogNavGraph(navController)

        cartNavGraph(navController)
    }
}

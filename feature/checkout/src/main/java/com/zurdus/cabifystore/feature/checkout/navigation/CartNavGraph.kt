package com.zurdus.cabifystore.feature.checkout.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zurdus.cabifystore.feature.checkout.ui.CheckoutScreen
import com.zurdus.cabifystore.navigation.NavGraphRoot

fun NavGraphBuilder.cartNavGraph(navController: NavController) {
    navigation(startDestination = ROUTE_CART, route = NavGraphRoot.CartGraph.name) {
        composable(ROUTE_CART) { CheckoutScreen(navController) }
    }
}

const val ROUTE_CART = "cart"

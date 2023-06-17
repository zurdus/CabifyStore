package com.zurdus.cabifystore.feature.cart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zurdus.cabifystore.feature.cart.ui.CartScreen

fun NavGraphBuilder.cartNavGraph(navController: NavController) {
    navigation(startDestination = ROUTE_CART, route = CART_ROOT) {
        composable(ROUTE_CART) { CartScreen(navController) }
    }
}

fun NavController.navigateToCart() {
    navigate(ROUTE_CART)
}

const val CART_ROOT = "cart_navgraph"

const val ROUTE_CART = "cart"

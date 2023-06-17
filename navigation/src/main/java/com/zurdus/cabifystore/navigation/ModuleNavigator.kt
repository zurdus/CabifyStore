package com.zurdus.cabifystore.navigation

import androidx.navigation.NavController

fun NavController.navigateToCart() {
    navigate(NavGraphRoot.CartGraph.name)
}

sealed class NavGraphRoot(val name: String) {
    object CatalogGraph : NavGraphRoot("catalog_graph")

    object CartGraph : NavGraphRoot("cart_graph")
}

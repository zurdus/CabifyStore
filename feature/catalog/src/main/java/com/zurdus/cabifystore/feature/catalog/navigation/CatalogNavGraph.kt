package com.zurdus.cabifystore.feature.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zurdus.cabifystore.feature.catalog.ui.CatalogDetailScreen
import com.zurdus.cabifystore.feature.catalog.ui.CatalogScreen
import com.zurdus.cabifystore.model.Product

fun NavGraphBuilder.catalogNavGraph(navController: NavController) {
    navigation(startDestination = ROUTE_CATALOG, CATALOG_ROOT) {
        composable(ROUTE_CATALOG) { CatalogScreen(navController) }

        dialog(
            route = ROUTE_DETAIL,
            arguments = listOf(
                navArgument(name = ARG_DETAIL_PRODUCT) { type = ProductType() },
                navArgument(name = ARG_DETAIL_INDEX) { type = NavType.IntType },
            )
        ) { entry ->
            val parcelableProduct = entry.arguments?.getParcelable<ParcelableProduct>(
                ARG_DETAIL_PRODUCT
            )
            val index = entry.arguments?.getInt(ARG_DETAIL_INDEX) ?: 0

            parcelableProduct?.let {
                CatalogDetailScreen(
                    ParcelableProduct.toProduct(parcelableProduct),
                    index,
                    navController
                )
            }
        }
    }
}

fun NavController.navigateToDetail(product: Product, index: Int) {
    val parcelableProduct = ParcelableProduct.fromProduct(product)
    val route = ROUTE_DETAIL
        .replace(ARG_DETAIL_INDEX, index.toString())
        .replace(ARG_DETAIL_PRODUCT, parcelableProduct.toString())

    navigate(route)
}

const val CATALOG_ROOT = "catalog_navgraph"

private const val ARG_DETAIL_INDEX = "index"
private const val ARG_DETAIL_PRODUCT = "product"

private const val ROUTE_CATALOG = "catalog"
private const val ROUTE_DETAIL = "catalog/{$ARG_DETAIL_INDEX}/{$ARG_DETAIL_PRODUCT}"

package com.zurdus.cabifystore.feature.checkout.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.model.Cart
import org.koin.androidx.compose.getViewModel

@Composable
fun CheckoutScreen(
    navController: NavController,
) {
    val viewModel: CheckoutViewModel = getViewModel()

    val cart by viewModel.cart.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = cart.toString(),
            style = CabifyTheme.typography.body1,
        )
    }
}

@Composable
private fun CartItem(

) {

}

package com.zurdus.cabifystore.feature.checkout.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zurdus.base.ui.theme.CabifyTheme

@Composable
fun CartScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "This is the catalog",
            style = CabifyTheme.typography.h3,
        )
    }
}

@Composable
private fun CartItem(

) {

}

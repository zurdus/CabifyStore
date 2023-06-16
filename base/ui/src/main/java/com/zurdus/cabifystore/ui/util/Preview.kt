package com.zurdus.base.ui.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zurdus.base.ui.theme.CabifyTheme

@Composable
fun Preview(
    backgroundColor: Color = MaterialTheme.colors.background,
    content: @Composable () -> Unit,
) {
    CabifyTheme {
        Surface(color = backgroundColor) {
            content()
        }
    }
}

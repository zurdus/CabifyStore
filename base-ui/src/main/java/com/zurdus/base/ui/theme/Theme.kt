package com.zurdus.base.ui.theme

import android.app.Activity
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CabifyTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val statusBarColor = CabifyTheme.color.main.background

        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalTypography provides cabifyTypography,
        LocalColors provides cabifyColors,
        LocalShapes provides Shapes(),
    ) {
        ProvideTextStyle(cabifyTypography.body1, content)
    }
}

object CabifyTheme {
    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val color: Colors
        @Composable
        get() = LocalColors.current

    val shape: Shapes
        @Composable
        get() = LocalShapes.current
}

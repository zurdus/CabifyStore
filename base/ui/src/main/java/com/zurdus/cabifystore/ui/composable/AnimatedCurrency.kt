package com.zurdus.cabifystore.ui.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import java.math.BigDecimal

@Composable
fun AnimatedCurrency(
    targetState: BigDecimal,
    content: @Composable AnimatedContentScope.(targetState: BigDecimal) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically { height -> height / 2 } + fadeIn()).togetherWith(
                    slideOutVertically { height -> -height / 2 } + fadeOut())
            } else {
                (slideInVertically { height -> -height / 2 } + fadeIn()).togetherWith(
                    slideOutVertically { height -> height / 2 } + fadeOut())
            }.using(
                SizeTransform(clip = false),
            )
        },
        content = content,
    )
}

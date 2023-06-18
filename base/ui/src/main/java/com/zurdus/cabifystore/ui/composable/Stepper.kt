package com.zurdus.cabifystore.ui.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.zurdus.base.ui.R
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.base.ui.theme.ColorSystem

@Composable
fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    colorSystem: ColorSystem = CabifyTheme.color.main,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    check(value in minValue..maxValue) { "Provided value is out of required bounds." }

    Stepper(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colorSystem = colorSystem,
        valueValidator = { newValue ->
            newValue in minValue..maxValue
        },
    )
}

@Composable
fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    colorSystem: ColorSystem = CabifyTheme.color.main,
    valueValidator: ((Int) -> Boolean)? = null,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onValueChange.invoke(value - 1) },
            enabled = valueValidator?.invoke(value - 1) ?: true,
            colorSystem = colorSystem,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_remove),
                contentDescription = null
            )
        }

        AnimatedContent(
            targetState = value,
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
        ) { targetNumber ->
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .widthIn(min = 24.dp),
                text = targetNumber.toString(),
                style = CabifyTheme.typography.subtitle2,
                color = colorSystem.content,
                textAlign = TextAlign.Center,
            )
        }

        StepperButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onValueChange.invoke(value + 1) },
            enabled = valueValidator?.invoke(value + 1) ?: true,
            colorSystem = colorSystem,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_add),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun StepperButton(
    onClick: () -> Unit,
    colorSystem: ColorSystem,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val background = if (enabled) {
        colorSystem.accent
    } else {
        colorSystem.accent.copy(alpha = 0.3f)
    }

    Box(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 20.dp),
            )
            .height(20.dp),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides CabifyTheme.typography.body1,
            LocalContentColor provides colorSystem.background,
        ) {
            Box(
                Modifier
                    .padding(2.dp)
                    .background(background, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun StepperPreview(
    @PreviewParameter(ColorSystemProvider::class) colorSystem: IColorSystem,
) {
    Preview {
        Box(Modifier.background(colorSystem.value().background)) {
            var i by remember { mutableIntStateOf(10) }
            Stepper(
                value = i,
                onValueChange = { i = it },
                maxValue = 10,
                colorSystem = colorSystem.value(),
            )
        }
    }
}

private class ColorSystemProvider : PreviewParameterProvider<IColorSystem> {
    override val values: Sequence<IColorSystem>
        get() = sequenceOf(
            IColorSystem { CabifyTheme.color.main},
            IColorSystem { CabifyTheme.color.mainInverse},
            IColorSystem { CabifyTheme.color.pink},
            IColorSystem { CabifyTheme.color.blue},
            IColorSystem { CabifyTheme.color.yellow},
            IColorSystem { CabifyTheme.color.orange},
            IColorSystem { CabifyTheme.color.moradul},
            IColorSystem { CabifyTheme.color.green},
            IColorSystem { CabifyTheme.color.red},
        )
}

data class IColorSystem(val value: @Composable () -> ColorSystem)

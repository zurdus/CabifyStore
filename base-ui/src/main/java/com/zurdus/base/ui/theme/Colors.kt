package com.zurdus.base.ui.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

val Moradul100 = Color(0xFFF3EDFF)
val Moradul600 = Color(0xFF7145D6)
val Moradul800 = Color(0xFF44307A)

val Blue100 = Color(0xFFE5F0FF)
val Blue500 = Color(0xFF4694FA)
val Blue800 = Color(0xFF294773)

val Green100 = Color(0xFFE6FAF2)
val Green500 = Color(0xFF41CC94)
val Green800 = Color(0xFF275252)

val Orange100 = Color(0xFFFEF0EA)
val Orange500 = Color(0xFFFC8549)
val Orange800 = Color(0xFF5F464E)

val Pink100 = Color(0xFFFFEBF5)
val Pink500 = Color(0xFFF55DAE)
val Pink800 = Color(0xFF633457)

val Red100 = Color(0xFFFFECEB)
val Red500 = Color(0xFFFD665B)
val Red800 = Color(0xFF5C3747)

val Yellow100 = Color(0xFFFFF5DE)
val Yellow500 = Color(0xFFF3AF13)
val Yellow800 = Color(0xFF5C4F42)



@Stable
class Colors(
    val main: ColorSystem,
    val mainInverse: ColorSystem,
    val neutral: ColorSystem,
    val blue: ColorSystem,
    val green: ColorSystem,
    val moradul: ColorSystem,
    val orange: ColorSystem,
    val pink: ColorSystem,
    val red: ColorSystem,
    val yellow: ColorSystem,
)

@Stable
class ColorSystem(
    val background: Color,
    val content: Color,
    val accent: Color,
)

val cabifyColors: Colors = Colors(
    main = ColorSystem(background = White, content = Moradul600, accent = Pink500),
    mainInverse = ColorSystem(background = Moradul600, content = White, accent = Yellow500),
    neutral = ColorSystem(background = White, content = Black, accent = Pink500),
    blue = ColorSystem(background = Blue100, content = Blue800, accent = Blue500),
    green = ColorSystem(background = Green100, content = Green800, accent = Green500),
    moradul = ColorSystem(background = Moradul100, content = Moradul800, accent = Moradul600),
    orange = ColorSystem(background = Orange100, content = Orange800, accent = Orange500),
    pink = ColorSystem(background = Pink100, content = Pink800, accent = Pink500),
    red = ColorSystem(background = Red100, content = Red800, accent = Red500),
    yellow = ColorSystem(background = Yellow100, content = Yellow800, accent = Yellow500),
)

internal val LocalColors: ProvidableCompositionLocal<Colors> =
    staticCompositionLocalOf { cabifyColors }

package org.bandev.buddhaquotescompose.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

internal const val SpringDefaultDampingRatio = Spring.DampingRatioMediumBouncy
internal const val SpringDefaultStiffness = Spring.StiffnessLow

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = Teal200,
    background = DarkBackground
)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = Teal200,
    background = LightBackground
)

@Composable
fun BuddhaQuotesComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun EdgeToEdgeContent(content: @Composable () -> Unit) {
    val isLightTheme = MaterialTheme.colors.isLight
    val controller = rememberSystemUiController()
    SideEffect {
        controller.setSystemBarsColor(color = Color.Transparent, darkIcons = isLightTheme)
    }
    ProvideWindowInsets(content = content)
}
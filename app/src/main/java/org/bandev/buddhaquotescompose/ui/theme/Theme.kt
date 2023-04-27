package org.bandev.buddhaquotescompose.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

internal const val SpringDefaultDampingRatio = Spring.DampingRatioMediumBouncy
internal const val SpringDefaultStiffness = Spring.StiffnessLow

private val DarkColorPalette = darkColorScheme(
    background = DarkBackground
)

private val LightColorPalette = lightColorScheme(
    background = LightBackground
)

@Composable
fun BuddhaQuotesComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun EdgeToEdgeContent(content: @Composable () -> Unit) {
    val isLightTheme = MaterialTheme.colorScheme == LightColorPalette
    val controller = rememberSystemUiController()
    SideEffect {
        controller.setSystemBarsColor(color = Color.Transparent, darkIcons = isLightTheme)
    }
    content()
}
package cz.uhk.umte.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkRed,
    primaryVariant = LightRed,
    secondary = Gold,
    secondaryVariant = DarkGray,
    background = DarkGray,
    error = SoftBlue,
    surface = DarkGray,
    onSecondary = White,
)

private val LightColorPalette = lightColors(
    primary = DarkRed,
    primaryVariant = LightRed,
    secondary = Gold,
    secondaryVariant = Gray,
    background = Gray,
    error = SoftBlue,
    onPrimary = Black,
    surface = Gray,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun UMTETheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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
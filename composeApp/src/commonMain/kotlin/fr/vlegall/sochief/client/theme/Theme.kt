package fr.vlegall.sochief.client.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFF97316),
    surfaceContainerHighest = Color(0xFFFFFFFF),
)


private val DarkColors = lightColorScheme(
    primary = Color(0xFFF97316),
    surfaceContainerHighest = Color(0xFFFFFFFF),
)


@Composable
fun SoChiefTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) DarkColors else LightColors
    val extras = if (darkTheme) DarkExtraColors else LightExtraColors

    CompositionLocalProvider(LocalSoChiefExtraColors provides extras) {
        MaterialTheme(
            colorScheme = scheme,
            content = content
        )
    }
}
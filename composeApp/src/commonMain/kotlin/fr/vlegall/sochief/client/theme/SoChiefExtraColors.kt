package fr.vlegall.sochief.client.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class SoChiefExtraColors(
    val customBlue: Color,
    val danger: Color
)

val LocalSoChiefExtraColors = staticCompositionLocalOf {
    SoChiefExtraColors(
        customBlue = Color(0xFF1D4ED8),
        danger = Color(0xFFDC2626)
    )
}

val LightExtraColors = SoChiefExtraColors(
    customBlue = Color(0xFF1D4ED8),
    danger = Color(0xFFDC2626)
)

val DarkExtraColors = SoChiefExtraColors(
    customBlue = Color(0xFF60A5FA),
    danger = Color(0xFFF87171)
)
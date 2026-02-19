package fr.vlegall.sochief.client.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class SoChiefExtraColors(
    val danger: Color
)

val LocalSoChiefExtraColors = staticCompositionLocalOf {
    SoChiefExtraColors(
        danger = Color(0xFFDC2626)
    )
}

val LightExtraColors = SoChiefExtraColors(
    danger = Color(0xFFDC2626)
)

val DarkExtraColors = SoChiefExtraColors(
    danger = Color(0xFFF87171)
)
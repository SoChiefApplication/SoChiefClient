package fr.vlegall.sochief.client.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object SoChiefThemeExtras {
    val colors: SoChiefExtraColors
        @Composable @ReadOnlyComposable
        get() = LocalSoChiefExtraColors.current
}
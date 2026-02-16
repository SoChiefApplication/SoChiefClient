package fr.vlegall.sochief.client.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    // Accent (boutons, chip active, highlight)
    primary = Color(0xFFF97316),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDD5),   // orange très clair (chips/containers)
    onPrimaryContainer = Color(0xFF7C2D12),

    // Neutres (la maquette est très neutre)
    background = Color(0xFFF6F7F9),         // fond app (gris très clair)
    onBackground = Color(0xFF0F172A),

    surface = Color(0xFFFFFFFF),            // cartes / surfaces
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),     // chips inactives / blocs secondaires
    onSurfaceVariant = Color(0xFF334155),

    outline = Color(0xFFE2E8F0),            // bordures (cards, textfields)
    outlineVariant = Color(0xFFCBD5E1),

    // Accents secondaires (optionnels)
    secondary = Color(0xFF0EA5E9),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF075985),

    tertiary = Color(0xFF22C55E),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDCFCE7),
    onTertiaryContainer = Color(0xFF166534),

    // Feedback
    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
)


private val DarkColors = lightColorScheme(
    // Accent (boutons, chip active, highlight)
    primary = Color(0xFFF97316),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDD5),   // orange très clair (chips/containers)
    onPrimaryContainer = Color(0xFF7C2D12),

    // Neutres (la maquette est très neutre)
    background = Color(0xFFF6F7F9),         // fond app (gris très clair)
    onBackground = Color(0xFF0F172A),

    surface = Color(0xFFFFFFFF),            // cartes / surfaces
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),     // chips inactives / blocs secondaires
    onSurfaceVariant = Color(0xFF334155),

    outline = Color(0xFFE2E8F0),            // bordures (cards, textfields)
    outlineVariant = Color(0xFFCBD5E1),

    // Accents secondaires (optionnels)
    secondary = Color(0xFF0EA5E9),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF075985),

    tertiary = Color(0xFF22C55E),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDCFCE7),
    onTertiaryContainer = Color(0xFF166534),

    // Feedback
    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
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
package fr.vlegall.sochief.client.plateform

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

actual object PlatformDimens {
    actual val configurationCardModifier: Modifier
        get() = Modifier.padding(16.dp)
    actual val headerModifier: Modifier
        get() = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)

    actual val limitsModifier: Modifier
        get() = Modifier.padding(16.dp)

}
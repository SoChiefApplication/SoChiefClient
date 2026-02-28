package fr.vlegall.sochief.client.plateform

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

actual object PlatformDimens {
    actual val configurationCardModifier: Modifier
        get() = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 64.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .widthIn(max = 700.dp)
    actual val headerModifier: Modifier
        get() = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .widthIn(max = 1400.dp)

    actual val limitsModifier: Modifier
        get() = Modifier
            .widthIn(max = 1400.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
}
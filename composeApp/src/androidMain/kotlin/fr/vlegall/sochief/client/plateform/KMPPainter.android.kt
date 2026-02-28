package fr.vlegall.sochief.client.plateform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import fr.vlegall.sochief.client.R

@Composable
actual fun appLogoPainter(): Painter =
    painterResource(R.drawable.app_icon)
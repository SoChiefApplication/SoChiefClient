package fr.vlegall.sochief.client.plateform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import sochief.composeapp.generated.resources.Res
import sochief.composeapp.generated.resources.appSvg

@Composable
actual fun appLogoPainter(): Painter =
    painterResource(Res.drawable.appSvg)
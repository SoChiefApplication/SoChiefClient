package fr.vlegall.sochief.client

// IMPORTANT : ces imports dépendent du package généré.
// On ne le devine pas, on l’utilise via auto-import IntelliJ (voir juste après).
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import sochief.composeapp.generated.resources.Res
import sochief.composeapp.generated.resources.app

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SoChief",
        icon = painterResource(Res.drawable.app)
    ) {
        App()
    }
}

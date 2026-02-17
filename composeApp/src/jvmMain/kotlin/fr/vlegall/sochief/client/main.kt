package fr.vlegall.sochief.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.vlegall.sochief.client.configuration.AppDependencies
import fr.vlegall.sochief.client.data.SecureStore
import org.jetbrains.compose.resources.painterResource
import sochief.composeapp.generated.resources.Res
import sochief.composeapp.generated.resources.app
import java.nio.file.Paths

fun main() = application {
    val appDir = Paths.get(
        System.getProperty("user.home"),
        ".sochief"
    )

    val secureStore = SecureStore(
        appDir = appDir,
        keystorePassword = "sochief".toCharArray()
    )

    val deps = AppDependencies(secureStore)

    Window(
        onCloseRequest = ::exitApplication,
        title = "SoChief",
        icon = painterResource(Res.drawable.app)
    ) {
        App(deps)
    }
}

package fr.vlegall.sochief.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.vlegall.sochief.client.configuration.ApiConfigCache
import fr.vlegall.sochief.client.configuration.ApiConfigRepository
import fr.vlegall.sochief.client.configuration.ApiConfigService
import fr.vlegall.sochief.client.configuration.isValid
import fr.vlegall.sochief.client.data.SecureStore
import fr.vlegall.sochief.client.navigation.AppRoute
import fr.vlegall.sochief.client.navigation.NavController
import fr.vlegall.sochief.client.navigation.middleware.ConfigurationMiddleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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

    val cache = ApiConfigCache()
    val repo = ApiConfigRepository(secureStore)
    val apiConfigService = ApiConfigService(repo, cache)

    val nav = NavController(AppRoute.Home)
    nav.withMiddleware(ConfigurationMiddleware { apiConfigService.current().isValid() })

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    scope.launch {
        apiConfigService.refreshFromStorage()
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "SoChief",
        icon = painterResource(Res.drawable.app)
    ) {
        App(apiConfigService, nav)
    }
}

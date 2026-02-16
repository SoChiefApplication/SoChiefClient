package fr.vlegall.sochief.client

// IMPORTANT : ces imports dépendent du package généré.
// On ne le devine pas, on l’utilise via auto-import IntelliJ (voir juste après).
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.vlegall.sochief.client.api.*
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
        keystorePassword = "sochief".toCharArray() // tu pourras améliorer plus tard
    )

    val repo = ApiConfigRepository(secureStore)
    val load = LoadApiConfig(repo)
    val save = SaveApiConfig(repo)

    val provider = RepositoryApiConfigProvider(repo)
    val apiService = RecipeApiService(provider)

    val deps = AppDependencies(
        loadApiConfig = load,
        saveApiConfig = save,
        recipeApiService = apiService
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "SoChief",
        icon = painterResource(Res.drawable.app)
    ) {
        App(deps)
    }
}

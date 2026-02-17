package fr.vlegall.sochief.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import fr.vlegall.sochief.client.configuration.AppDependencies
import fr.vlegall.sochief.client.configuration.MemoryApiConfigProvider
import fr.vlegall.sochief.client.configuration.RecipeApiService
import fr.vlegall.sochief.client.theme.SoChiefTheme
import fr.vlegall.sochief.client.view.MainApp
import kotlinx.coroutines.launch

@Composable
fun App(deps: AppDependencies) {
    val scope = rememberCoroutineScope()

    SoChiefTheme {
        ApiConfigGate(
            loadApiConfig = deps.loadApiConfig,
            saveApiConfig = deps.saveApiConfig,
            clearApiConfig = deps.clearApiConfig,
            memoryProvider = deps.memoryProvider
        ) { cfg, onClear ->
            MainApp(
                recipeApi = RecipeApiService(MemoryApiConfigProvider(cfg)),
                onClearConfig = { scope.launch { onClear() } }
            )
        }
    }
}
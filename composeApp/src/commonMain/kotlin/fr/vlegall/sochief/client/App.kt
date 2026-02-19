package fr.vlegall.sochief.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import fr.vlegall.sochief.client.configuration.ApiConfigService
import fr.vlegall.sochief.client.configuration.RecipeApiService
import fr.vlegall.sochief.client.navigation.AppRoute
import fr.vlegall.sochief.client.navigation.NavController
import fr.vlegall.sochief.client.theme.SoChiefTheme
import fr.vlegall.sochief.client.view.ConfigurationView
import fr.vlegall.sochief.client.view.MainApp
import kotlinx.coroutines.launch

@Composable
fun App(
    apiConfigService: ApiConfigService,
    nav: NavController
) {
    LaunchedEffect(Unit) {
        nav.bootstrap()
    }

    val scope = rememberCoroutineScope()

    SoChiefTheme {
        when (val route = nav.current) {

            AppRoute.Home -> MainApp(
                recipeApi = RecipeApiService(apiConfigService),
                onClearConfig = {
                    scope.launch {
                        apiConfigService.clear()
                    }
                    nav.navigate(AppRoute.Configuration)
                }
            )

            AppRoute.Configuration -> {
                ConfigurationView(apiConfigService, nav)
            }
        }
    }
}
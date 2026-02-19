package fr.vlegall.sochief.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val cache = ApiConfigCache()
        val repo = ApiConfigRepository(SecureStore(applicationContext))
        val apiConfigService = ApiConfigService(repo, cache)

        val nav = NavController(AppRoute.Home)
        nav.withMiddleware(ConfigurationMiddleware { apiConfigService.current().isValid() })

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        scope.launch {
            apiConfigService.refreshFromStorage()
        }

        setContent {
            BackHandler(enabled = nav.canGoBack()) {
                nav.goBack()
            }
            App(apiConfigService, nav)
        }
    }
}
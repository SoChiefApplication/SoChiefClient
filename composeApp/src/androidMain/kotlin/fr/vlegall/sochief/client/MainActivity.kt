package fr.vlegall.sochief.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.vlegall.sochief.client.api.*
import fr.vlegall.sochief.client.data.SecureStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val store = SecureStore(applicationContext)
        val repo = ApiConfigRepository(store)
        val load = LoadApiConfig(repo)
        val save = SaveApiConfig(repo)

        val provider = RepositoryApiConfigProvider(repo)
        val api = RecipeApiService(provider)

        val deps = AppDependencies(
            loadApiConfig = load,
            saveApiConfig = save,
            recipeApiService = api
        )

        setContent {
            App(deps)
        }
    }
}
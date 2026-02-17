package fr.vlegall.sochief.client.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.vlegall.sochief.client.configuration.NamedIdDto
import fr.vlegall.sochief.client.configuration.RecipeApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    recipeApi: RecipeApiService,
    onClearConfig: () -> Unit
) {
    var categories by remember { mutableStateOf<List<NamedIdDto>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(recipeApi) {
        isLoading = true
        error = null
        try {
            categories = recipeApi.getRecipeCategories()
        } catch (e: Exception) {
            error = e.message ?: e.toString()
            categories = null
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SoChief") },
                actions = {
                    TextButton(onClick = onClearConfig) { Text("Config API") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Application principale", style = MaterialTheme.typography.titleLarge)

            when {
                isLoading -> {
                    CircularProgressIndicator()
                    Text("Chargement des catégories…")
                }

                error != null -> {
                    Text("Erreur: $error", color = MaterialTheme.colorScheme.error)
                    Button(onClick = { /* relance */ categories = null }) {
                        Text("Réessayer")
                    }
                }

                categories != null -> {
                    Text("Catégories (${categories!!.size}) :")
                    categories!!.forEach { c ->
                        Text("• ${c.name} (#${c.id})")
                    }
                }

                else -> {
                    Text("Aucune donnée.")
                }
            }

            Button(onClick = onClearConfig) {
                Text("Changer la config API")
            }
        }
    }
}

package fr.vlegall.sochief.client.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.vlegall.sochief.client.components.ui.home.HomeTopBar
import fr.vlegall.sochief.client.configuration.RecipeApiService
import fr.vlegall.sochief.client.plateform.PlatformDimens
import fr.vlegall.sochief.contracts.common.NamedIdDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
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
            HomeTopBar(
                onSettings = {},
                onNewRecipe = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = PlatformDimens.limitsModifier
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
    }
}

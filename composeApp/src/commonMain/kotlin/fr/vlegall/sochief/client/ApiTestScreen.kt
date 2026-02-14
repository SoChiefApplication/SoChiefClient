package fr.vlegall.sochief.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ApiTestScreen() {
    var baseUrl by remember { mutableStateOf(fr.vlegall.sochief.client.data.ApiConfig.baseUrl) }
    var apiKey by remember { mutableStateOf(fr.vlegall.sochief.client.data.ApiConfig.apiKey) }
    var testResult by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val apiService = remember { _root_ide_package_.fr.vlegall.sochief.client.data.RecipeApiService() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Test API Configuration",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Configuration section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = baseUrl,
                    onValueChange = { baseUrl = it },
                    label = { Text("Base URL") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        fr.vlegall.sochief.client.data.ApiConfig.setConfiguration(baseUrl, apiKey)
                        testResult = "Configuration sauvegardée!"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sauvegarder Configuration")
                }
            }
        }

        // Test buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tests API",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val difficulties = apiService.getRecipeDifficulties()
                                    testResult = "Difficultés récupérées: ${difficulties.size} éléments"
                                } catch (e: Exception) {
                                    testResult = "Erreur: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Difficultés")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val categories = apiService.getRecipeCategories()
                                    testResult = "Catégories récupérées: ${categories.size} éléments"
                                } catch (e: Exception) {
                                    testResult = "Erreur: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Catégories")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val pageable = _root_ide_package_.fr.vlegall.sochief.client.data.Pageable(
                                        page = 0,
                                        size = 10,
                                        sort = listOf("title")
                                    )
                                    val recipes = apiService.searchRecipes(null, null, pageable)
                                    testResult = "Recettes trouvées: ${recipes.totalElements} éléments"
                                } catch (e: Exception) {
                                    testResult = "Erreur: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Chercher")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val recipe = apiService.getRecipeById(1)
                                    testResult = "Recette récupérée: ${recipe.title}"
                                } catch (e: Exception) {
                                    testResult = "Erreur: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Détail ID=1")
                    }
                }

                Button(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            try {
                                val newRecipe =
                                    _root_ide_package_.fr.vlegall.sochief.client.data.RecipeUpsertRequestDto(
                                        title = "Test Recipe",
                                        description = "A test recipe created from the app",
                                        categoryId = 1,
                                        difficultyId = 1,
                                        initialPortions = 4,
                                        preparationTime = "PT30M",
                                        cookingTime = "PT45M",
                                        ingredients = listOf(
                                            _root_ide_package_.fr.vlegall.sochief.client.data.RecipeIngredientUpsertDto(
                                                ingredient = _root_ide_package_.fr.vlegall.sochief.client.data.IdOrNameDto(
                                                    id = 1,
                                                    name = null
                                                ),
                                                unit = _root_ide_package_.fr.vlegall.sochief.client.data.IdOrNameDto(
                                                    id = 1,
                                                    name = null
                                                ),
                                                quantity = 2.0
                                            )
                                        ),
                                        steps = listOf(
                                            _root_ide_package_.fr.vlegall.sochief.client.data.RecipeStepUpsertDto(
                                                description = "Mix ingredients",
                                                duration = "PT10M",
                                                position = 1
                                            )
                                        ),
                                        tags = listOf(),
                                        utensils = listOf()
                                    )
                                val created = apiService.createRecipe(newRecipe)
                                testResult = "Recette créée: ${created.title} (ID: ${created.id})"
                            } catch (e: Exception) {
                                testResult = "Erreur: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text("Créer Recette Test")
                }
            }
        }

        // Results section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Résultats",
                    style = MaterialTheme.typography.titleMedium
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = testResult.ifEmpty { "Aucun test effectué" },
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
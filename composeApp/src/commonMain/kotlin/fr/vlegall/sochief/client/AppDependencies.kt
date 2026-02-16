package fr.vlegall.sochief.client

import fr.vlegall.sochief.client.api.LoadApiConfig
import fr.vlegall.sochief.client.api.RecipeApiService
import fr.vlegall.sochief.client.api.SaveApiConfig

data class AppDependencies(
    val loadApiConfig: LoadApiConfig,
    val saveApiConfig: SaveApiConfig,
    val recipeApiService: RecipeApiService
)
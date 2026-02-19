package fr.vlegall.sochief.client.navigation

sealed interface AppRoute {
    data object Home : AppRoute
    data object Configuration : AppRoute
//    data class RecipeDetail(val recipeId: String) : AppRoute
}
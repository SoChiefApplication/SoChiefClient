package fr.vlegall.sochief.client.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class NamedIdDto(
    val id: Long,
    val name: String
)

@Serializable
data class RecipeIngredientDto(
    val ingredient: NamedIdDto,
    val unit: NamedIdDto,
    val quantity: Double
)

@Serializable
data class RecipeStepDto(
    val id: Long,
    val description: String,
    val duration: String,
    val position: Int
)

@Serializable
data class RecipeDetailDto(
    val id: Long,
    val title: String,
    val description: String,
    val category: NamedIdDto,
    val difficulty: NamedIdDto,
    val initialPortions: Int,
    val displayedPortions: Int,
    val preparationTime: String,
    val cookingTime: String,
    val ingredients: List<RecipeIngredientDto>,
    val steps: List<RecipeStepDto>,
    val tags: List<NamedIdDto>,
    val utensils: List<NamedIdDto>
)

@Serializable
data class IdOrNameDto(
    val id: Long? = null,
    val name: String? = null
)

@Serializable
data class RecipeIngredientUpsertDto(
    val ingredient: IdOrNameDto,
    val unit: IdOrNameDto,
    val quantity: Double
)

@Serializable
data class RecipeStepUpsertDto(
    val description: String,
    val duration: String,
    val position: Int
)

@Serializable
data class RecipeUpsertRequestDto(
    val title: String,
    val description: String,
    val categoryId: Long,
    val difficultyId: Long,
    val initialPortions: Int,
    val preparationTime: String,
    val cookingTime: String,
    val ingredients: List<RecipeIngredientUpsertDto>,
    val steps: List<RecipeStepUpsertDto>,
    val tags: List<IdOrNameDto>,
    val utensils: List<IdOrNameDto>
)

@Serializable
data class Pageable(
    val page: Int,
    val size: Int,
    val sort: List<String>
)

@Serializable
data class RecipeListItemDto(
    val id: Long,
    val title: String,
    val category: NamedIdDto,
    val difficulty: NamedIdDto,
    val initialPortions: Int,
    val preparationTime: String,
    val cookingTime: String
)

@Serializable
data class PageableObject(
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Long,
    val sort: SortObject,
    val unpaged: Boolean
)

@Serializable
data class SortObject(
    val sorted: Boolean,
    val empty: Boolean,
    val unsorted: Boolean
)

@Serializable
data class PageRecipeListItemDto(
    val totalElements: Long,
    val totalPages: Int,
    val pageable: PageableObject,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
    val content: List<RecipeListItemDto>,
    val number: Int,
    val sort: SortObject,
    val numberOfElements: Int,
    val empty: Boolean
)

class RecipeApiService {

    init {
        // Charger la configuration au démarrage
        ApiConfig.loadConfiguration()
    }

    private val client = HttpClient(CIO) {
        defaultRequest {
            url(ApiConfig.baseUrl)
            header("X-API-KEY", ApiConfig.apiKey)
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getRecipeById(id: Long, portions: Int? = null): RecipeDetailDto {
        return client.get("recipes/$id") {
            if (portions != null) {
                parameter("portions", portions)
            }
        }.body()
    }

    suspend fun updateRecipe(
        id: Long,
        recipe: RecipeUpsertRequestDto
    ): RecipeDetailDto {
        return client.put("recipes/$id") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    suspend fun deleteRecipe(id: Long) {
        client.delete("recipes/$id")
    }

    suspend fun searchRecipes(
        q: String?,
        categoryId: Long?,
        pageable: Pageable
    ): PageRecipeListItemDto {
        return client.get("recipes") {
            if (q != null) {
                parameter("q", q)
            }
            if (categoryId != null) {
                parameter("categoryId", categoryId)
            }
            parameter("page", pageable.page)
            parameter("size", pageable.size)
            parameter("sort", pageable.sort)
        }.body()
    }

    suspend fun createRecipe(recipe: RecipeUpsertRequestDto): RecipeDetailDto {
        return client.post("recipes") {
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    suspend fun getRecipeDifficulties(): List<NamedIdDto> {
        return client.get("recipes/difficulty").body()
    }

    suspend fun getRecipeCategories(): List<NamedIdDto> {
        return client.get("recipes/category").body()
    }
}

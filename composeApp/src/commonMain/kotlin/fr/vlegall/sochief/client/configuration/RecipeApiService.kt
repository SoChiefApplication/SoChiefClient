package fr.vlegall.sochief.client.configuration

import fr.vlegall.sochief.contracts.common.NamedIdDto
import fr.vlegall.sochief.contracts.request.RecipeUpsertRequestDto
import fr.vlegall.sochief.contracts.response.RecipeDetailDto
import fr.vlegall.sochief.contracts.response.RecipeListItemDto
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
data class Pageable(
    val page: Int,
    val size: Int,
    val sort: List<String>
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

class RecipeApiService(
    private val apiConfigService: ApiConfigService,
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 10_000
        }
    }
) {
    private fun requireConfig(): ApiConfig =
        apiConfigService.current() ?: error("API config missing (baseUrl/apiKey)")

    private fun joinUrl(base: String, path: String): String =
        base.trimEnd('/') + "/" + path.trimStart('/')

    private fun withAuth(builder: HttpRequestBuilder) {
        val cfg = requireConfig()
        builder.url(joinUrl(cfg.baseUrl, builder.url.encodedPath))
        builder.headers.append("X-API-KEY", cfg.apiKey)
    }

    suspend fun getRecipeById(id: Long, portions: Int? = null): RecipeDetailDto {
        val cfg = requireConfig()
        return client.get(joinUrl(cfg.baseUrl, "recipes/$id")) {
            header("X-API-KEY", cfg.apiKey)
            if (portions != null) parameter("portions", portions)
        }.body()
    }

    suspend fun updateRecipe(id: Long, recipe: RecipeUpsertRequestDto): RecipeDetailDto {
        val cfg = requireConfig()
        return client.put(joinUrl(cfg.baseUrl, "recipes/$id")) {
            header("X-API-KEY", cfg.apiKey)
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    suspend fun deleteRecipe(id: Long) {
        val cfg = requireConfig()
        client.delete(joinUrl(cfg.baseUrl, "recipes/$id")) {
            header("X-API-KEY", cfg.apiKey)
        }
    }

    suspend fun searchRecipes(q: String?, categoryId: Long?, pageable: Pageable): PageRecipeListItemDto {
        val cfg = requireConfig()
        return client.get(joinUrl(cfg.baseUrl, "recipes")) {
            header("X-API-KEY", cfg.apiKey)
            if (q != null) parameter("q", q)
            if (categoryId != null) parameter("categoryId", categoryId)
            parameter("page", pageable.page)
            parameter("size", pageable.size)
            pageable.sort.forEach { parameter("sort", it) }
        }.body()
    }

    suspend fun createRecipe(recipe: RecipeUpsertRequestDto): RecipeDetailDto {
        val cfg = requireConfig()
        return client.post(joinUrl(cfg.baseUrl, "recipes")) {
            header("X-API-KEY", cfg.apiKey)
            contentType(ContentType.Application.Json)
            setBody(recipe)
        }.body()
    }

    suspend fun getRecipeDifficulties(): List<NamedIdDto> {
        val cfg = requireConfig()
        return client.get(joinUrl(cfg.baseUrl, "recipes/difficulty")) {
            header("X-API-KEY", cfg.apiKey)
        }.body()
    }

    suspend fun getRecipeCategories(): List<NamedIdDto> {
        val cfg = requireConfig()
        return client.get(joinUrl(cfg.baseUrl, "recipes/category")) {
            header("X-API-KEY", cfg.apiKey)
        }.body()
    }
}

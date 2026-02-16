package fr.vlegall.sochief.client.api

class SaveApiConfig(private val repo: ApiConfigRepository) {
    suspend operator fun invoke(baseUrl: String, apiKey: String) =
        repo.save(ApiConfig(baseUrl.trim(), apiKey.trim()))
}
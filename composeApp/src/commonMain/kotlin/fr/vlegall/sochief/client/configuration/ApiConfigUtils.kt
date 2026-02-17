package fr.vlegall.sochief.client.configuration

class ClearApiConfig(private val repo: ApiConfigRepository) {
    suspend operator fun invoke() =
        repo.clear()
}

class LoadApiConfig(private val repo: ApiConfigRepository) {
    suspend operator fun invoke(): ApiConfig? = repo.load()
}

class SaveApiConfig(private val repo: ApiConfigRepository) {
    suspend operator fun invoke(baseUrl: String, apiKey: String) =
        repo.save(ApiConfig(baseUrl.trim(), apiKey.trim()))
}
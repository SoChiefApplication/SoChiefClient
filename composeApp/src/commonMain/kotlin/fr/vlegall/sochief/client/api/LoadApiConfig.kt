package fr.vlegall.sochief.client.api

class LoadApiConfig(private val repo: ApiConfigRepository) {
    suspend operator fun invoke(): ApiConfig? = repo.load()
}
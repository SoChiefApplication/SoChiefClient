package fr.vlegall.sochief.client.configuration

class RepositoryApiConfigProvider(
    private val repo: ApiConfigRepository
) : ApiConfigProvider {
    override suspend fun get(): ApiConfig? = repo.load()
}
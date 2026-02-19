package fr.vlegall.sochief.client.configuration

class ApiConfigService(
    private val repo: ApiConfigRepository,
    private val cache: ApiConfigCache
) {
    fun current(): ApiConfig? = cache.get()

    suspend fun refreshFromStorage(): ApiConfig? {
        val loaded = repo.load()
        cache.update(loaded)
        return loaded
    }

    suspend fun save(config: ApiConfig) {
        repo.save(config)
        cache.update(config)
    }

    suspend fun clear() {
        repo.clear()
        cache.clear()
    }
}
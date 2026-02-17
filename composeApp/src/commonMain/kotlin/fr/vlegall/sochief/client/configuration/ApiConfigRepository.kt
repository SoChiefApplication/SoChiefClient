package fr.vlegall.sochief.client.configuration

import fr.vlegall.sochief.client.data.SecureStore

class ApiConfigRepository(
    private val store: SecureStore
) {
    private val KEY = "api_config_v1"

    suspend fun load(): ApiConfig? =
        store.getJson(KEY, ApiConfig.serializer())

    suspend fun save(config: ApiConfig) {
        store.putJson(KEY, ApiConfig.serializer(), config)
    }

    suspend fun clear() {
        store.remove(KEY)
    }
}
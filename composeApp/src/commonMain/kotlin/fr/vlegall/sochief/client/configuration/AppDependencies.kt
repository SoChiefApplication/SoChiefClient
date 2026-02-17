package fr.vlegall.sochief.client.configuration

import fr.vlegall.sochief.client.data.SecureStore

class AppDependencies(secureStore: SecureStore) {
    private val repo = ApiConfigRepository(secureStore)

    val loadApiConfig = LoadApiConfig(repo)
    val saveApiConfig = SaveApiConfig(repo)
    val clearApiConfig = ClearApiConfig(repo)

    val memoryProvider = MemoryApiConfigProvider()
}
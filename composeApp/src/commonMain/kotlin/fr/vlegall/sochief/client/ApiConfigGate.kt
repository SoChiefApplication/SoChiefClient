package fr.vlegall.sochief.client

import androidx.compose.runtime.*
import fr.vlegall.sochief.client.components.settings.ApiConfigView
import fr.vlegall.sochief.client.configuration.*

@Composable
fun ApiConfigGate(
    loadApiConfig: LoadApiConfig,
    saveApiConfig: SaveApiConfig,
    clearApiConfig: ClearApiConfig,
    memoryProvider: MemoryApiConfigProvider,
    appContent: @Composable (ApiConfig, onClear: suspend () -> Unit) -> Unit
) {
    var persistedConfig by remember { mutableStateOf<ApiConfig?>(null) }
    var isLoaded by remember { mutableStateOf(false) }

    suspend fun reload() {
        persistedConfig = loadApiConfig()
        memoryProvider.update(persistedConfig)
        isLoaded = true
    }

    LaunchedEffect(Unit) { reload() }

    if (!isLoaded) return

    if (!persistedConfig.isValid()) {
        var draftBaseUrl by remember(persistedConfig) { mutableStateOf(persistedConfig?.baseUrl.orEmpty()) }
        var draftApiKey by remember(persistedConfig) { mutableStateOf(persistedConfig?.apiKey.orEmpty()) }

        ApiConfigView(
            baseUrl = draftBaseUrl,
            apiKey = draftApiKey,
            onBaseUrlChange = { draftBaseUrl = it },
            onApiKeyChange = { draftApiKey = it },
            onSave = { url, key ->
                saveApiConfig(url, key)
                reload()
            }
        )
    } else {
        appContent(
            persistedConfig!!,
            {
                clearApiConfig()
                reload()
            }
        )
    }
}

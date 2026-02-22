package fr.vlegall.sochief.client.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.vlegall.sochief.client.components.settings.ApiConfigCard
import fr.vlegall.sochief.client.configuration.ApiConfig
import fr.vlegall.sochief.client.configuration.ApiConfigService
import fr.vlegall.sochief.client.navigation.AppRoute
import fr.vlegall.sochief.client.navigation.NavController
import fr.vlegall.sochief.client.plateform.PlatformDimens

@Composable
fun ConfigurationView(
    apiConfigService: ApiConfigService,
    nav: NavController
) {
    var draftBaseUrl by remember(apiConfigService.current()) { mutableStateOf(apiConfigService.current()?.baseUrl.orEmpty()) }
    var draftApiKey by remember(apiConfigService.current()) { mutableStateOf(apiConfigService.current()?.apiKey.orEmpty()) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ApiConfigCard(
            baseUrl = draftBaseUrl,
            apiKey = draftApiKey,
            onBaseUrlChange = { draftBaseUrl = it },
            onApiKeyChange = { draftApiKey = it },
            onSave = { url, key ->
                apiConfigService.save(ApiConfig(url, key))
                nav.navigate(AppRoute.Home)
            },
            modifier = PlatformDimens.configurationCardModifier
        )
    }

}
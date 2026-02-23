package fr.vlegall.sochief.client.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.vlegall.sochief.client.components.settings.ApiConfigCard
import fr.vlegall.sochief.client.configuration.ApiCallResult
import fr.vlegall.sochief.client.configuration.ApiConfig
import fr.vlegall.sochief.client.configuration.ApiConfigService
import fr.vlegall.sochief.client.configuration.RecipeApiService
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

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ApiConfigCard(
            baseUrl = draftBaseUrl,
            apiKey = draftApiKey,
            onBaseUrlChange = { draftBaseUrl = it },
            onApiKeyChange = { draftApiKey = it },
            errorMessage = errorMessage,
            onSave = { url, key ->
                errorMessage = null
                val api = RecipeApiService(
                    apiConfigService = apiConfigService,
                    overrideConfig = ApiConfig(url, key)
                )

                when (val res = api.getStatus()) {
                    is ApiCallResult.Ok -> {
                        if (res.value.healthy) {
                            apiConfigService.save(ApiConfig(url, key))
                            nav.navigate(AppRoute.Home)
                        } else {
                            errorMessage = "API non healthy"
                        }
                    }

                    is ApiCallResult.NeedsReconfigure -> {
                        draftApiKey = ""
                        errorMessage = res.message
                    }

                    is ApiCallResult.NetworkError -> {
                        draftBaseUrl = ""
                        draftApiKey = ""
                        errorMessage = res.message
                    }

                    is ApiCallResult.HttpError -> {
                        draftBaseUrl = ""
                        draftApiKey = ""
                        errorMessage = res.message
                    }

                    is ApiCallResult.UnexpectedError -> {
                        draftBaseUrl = ""
                        draftApiKey = ""
                        errorMessage = res.message
                    }
                }
            },
            modifier = PlatformDimens.configurationCardModifier
        )
    }

}
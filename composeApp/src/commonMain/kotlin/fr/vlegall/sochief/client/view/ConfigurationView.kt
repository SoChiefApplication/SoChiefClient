package fr.vlegall.sochief.client.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.vlegall.sochief.client.components.settings.ApiConfigCard
import fr.vlegall.sochief.client.configuration.ApiCallResult
import fr.vlegall.sochief.client.configuration.ApiConfig
import fr.vlegall.sochief.client.configuration.ApiConfigService
import fr.vlegall.sochief.client.configuration.RecipeApiService
import fr.vlegall.sochief.client.navigation.AppRoute
import fr.vlegall.sochief.client.navigation.NavController
import fr.vlegall.sochief.client.plateform.PlatformDimens
import fr.vlegall.sochief.client.plateform.appLogoPainter

@Composable
fun ConfigurationView(
    apiConfigService: ApiConfigService,
    nav: NavController
) {
    var draftBaseUrl by remember(apiConfigService.current()) { mutableStateOf(apiConfigService.current()?.baseUrl.orEmpty()) }
    var draftApiKey by remember(apiConfigService.current()) { mutableStateOf(apiConfigService.current()?.apiKey.orEmpty()) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFF7ED),
                    Color(0xFFFFEDD5)
                )
            )
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = PlatformDimens.configurationCardModifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp).fillMaxWidth(),
            ) {
                Image(
                    painter = appLogoPainter(),
                    contentDescription = "app icon",
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(80.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Bienvenue !",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Configurons votre application de recettes",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Spacer(Modifier.height(32.dp))
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
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }

}
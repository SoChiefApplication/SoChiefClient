package fr.vlegall.sochief.client.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Key
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Server
import fr.vlegall.sochief.client.components.ui.PasswordOutlinedTextField
import kotlinx.coroutines.launch

@Composable
fun ApiConfigCard(
    baseUrl: String,
    apiKey: String,
    onBaseUrlChange: (String) -> Unit,
    onApiKeyChange: (String) -> Unit,
    onSave: suspend (String, String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Configuration API",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = baseUrl,
            onValueChange = onBaseUrlChange,
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Lucide.Server,
                        contentDescription = "Server",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("URL API")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        PasswordOutlinedTextField(
            value = apiKey,
            onValueChange = onApiKeyChange,
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Lucide.Key,
                        contentDescription = "Key",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clé API")
                }

            },
            modifier = Modifier.fillMaxWidth()
        )

        if (!errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        onSave(baseUrl, apiKey)
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading && baseUrl.isNotBlank() && apiKey.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Vérification..." else "Sauvegarder")
        }
    }

}

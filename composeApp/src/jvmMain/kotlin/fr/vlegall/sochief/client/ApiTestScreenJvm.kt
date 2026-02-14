package fr.vlegall.sochief.client

import androidx.compose.runtime.*

@Composable
fun ApiTestScreenJvm() {
    var isConfigLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Charger la configuration depuis le fichier .env au démarrage
        fr.vlegall.sochief.client.data.PlatformConfig.loadFromEnvFile()
        isConfigLoaded = true
    }

    if (isConfigLoaded) {
        ApiTestScreen()
    } else {
        // Afficher un message de chargement
        androidx.compose.material3.Text("Chargement de la configuration...")
    }
}
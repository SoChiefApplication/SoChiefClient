package fr.vlegall.sochief.client

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(deps: AppDependencies) {
    var showApiTest by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "SoChef App",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Application de gestion de recettes",
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = { showApiTest = !showApiTest },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (showApiTest) "Cacher Test API" else "Afficher Test API")
                }
            }
        }

        if (showApiTest) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                ApiTestScreen(deps)
            }
        }
    }
}
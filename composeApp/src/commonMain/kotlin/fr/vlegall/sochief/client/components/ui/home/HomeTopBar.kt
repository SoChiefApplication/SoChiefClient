package fr.vlegall.sochief.client.components.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.vlegall.sochief.client.plateform.PlatformDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSettings: () -> Unit,
    onNewRecipe: () -> Unit,
) {
    TopAppBar(
        actions = {},
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RectangleShape,
            clip = false
        ),
        title = {
            BoxWithConstraints(
                modifier = PlatformDimens.headerModifier
            ) {
                val isCompact = maxWidth < 480.dp

                if (isCompact) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        HeaderText()
                        HomeHeaderActions(
                            onSettings = onSettings,
                            onNewRecipe = onNewRecipe,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderText()
                        HomeHeaderActions(
                            onSettings = onSettings,
                            onNewRecipe = onNewRecipe,
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun HeaderText() {
    Column {
        Text(
            "Mes Recettes",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Gérez et découvrez vos recettes préférées",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
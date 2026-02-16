package fr.vlegall.sochief.client

import androidx.compose.runtime.Composable
import fr.vlegall.sochief.client.theme.SoChiefTheme

@Composable
fun App(deps: AppDependencies) {
    SoChiefTheme {
        MainScreen(deps)
    }
}
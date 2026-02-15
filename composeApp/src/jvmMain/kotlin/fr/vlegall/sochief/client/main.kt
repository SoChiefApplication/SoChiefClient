package fr.vlegall.sochief.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    // Initialiser la configuration JVM
    JvmConfigInitializer.initialize()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SoChief",
        ) {
            App()
        }
    }
}
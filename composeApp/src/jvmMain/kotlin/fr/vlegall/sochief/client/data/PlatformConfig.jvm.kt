package fr.vlegall.sochief.client.data

import java.io.File
import java.util.*

actual object PlatformConfig {

    fun loadFromEnvFile(filePath: String = ".env") {
        val envFile = File(filePath)
        if (envFile.exists()) {
            val properties = Properties()
            envFile.inputStream().use { properties.load(it) }

            properties.getProperty("API_BASE_URL")
                ?.let { ApiConfig.baseUrl = it }
            properties.getProperty("API_KEY")
                ?.let { ApiConfig.apiKey = it }
        }
    }

    fun loadFromSystemProperties() {
        System.getProperty("api.base.url")
            ?.let { ApiConfig.baseUrl = it }
        System.getProperty("api.key")?.let { ApiConfig.apiKey = it }
    }

    actual fun loadPlatformSpecificConfig() {
        // Charger depuis le fichier .env par défaut
        loadFromEnvFile()
        // Charger depuis les propriétés système (peut écraser le fichier .env)
        loadFromSystemProperties()
    }
}
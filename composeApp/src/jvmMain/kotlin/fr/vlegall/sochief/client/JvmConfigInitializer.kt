package fr.vlegall.sochief.client

object JvmConfigInitializer {
    fun initialize() {
        // Charger la configuration depuis le fichier .env
        fr.vlegall.sochief.client.data.PlatformConfig.loadFromEnvFile()
    }
}
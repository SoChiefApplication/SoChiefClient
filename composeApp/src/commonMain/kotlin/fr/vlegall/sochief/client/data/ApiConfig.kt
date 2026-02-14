package fr.vlegall.sochief.client.data

object ApiConfig {
    // Configuration par défaut - peut être modifiée au runtime
    var baseUrl: String = "http://localhost:8080/api/"
    var apiKey: String = ""

    // Méthode pour charger la configuration depuis la plateforme spécifique
    fun loadConfiguration() {
        PlatformConfig.loadPlatformSpecificConfig()
    }

    // Méthode pour définir la configuration manuellement
    fun setConfiguration(baseUrl: String, apiKey: String) {
        this.baseUrl = baseUrl
        this.apiKey = apiKey
    }
}
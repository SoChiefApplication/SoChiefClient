package fr.vlegall.sochief.client.data

import android.content.Context
import android.content.SharedPreferences

actual object PlatformConfig {
    private const val PREFS_NAME = "api_config"
    private const val KEY_BASE_URL = "base_url"
    private const val KEY_API_KEY = "api_key"

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadFromPreferences()
    }

    private fun loadFromPreferences() {
        sharedPreferences?.let { prefs ->
            prefs.getString(KEY_BASE_URL, null)?.let { ApiConfig.baseUrl = it }
            prefs.getString(KEY_API_KEY, null)?.let { ApiConfig.apiKey = it }
        }
    }

    fun saveConfiguration(baseUrl: String, apiKey: String) {
        sharedPreferences?.edit()?.apply {
            putString(KEY_BASE_URL, baseUrl)
            putString(KEY_API_KEY, apiKey)
            apply()
        }
        ApiConfig.setConfiguration(baseUrl, apiKey)
    }

    actual fun loadPlatformSpecificConfig() {
        // La configuration est déjà chargée dans initialize()
    }
}
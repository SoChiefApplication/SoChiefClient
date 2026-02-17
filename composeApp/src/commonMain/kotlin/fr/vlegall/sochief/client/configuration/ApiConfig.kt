package fr.vlegall.sochief.client.configuration

import kotlinx.serialization.Serializable

@Serializable
data class ApiConfig(
    val baseUrl: String,
    val apiKey: String
)

fun ApiConfig?.isValid(): Boolean =
    this != null && baseUrl.isNotBlank() && apiKey.isNotBlank()

interface ApiConfigProvider {
    suspend fun get(): ApiConfig?
}
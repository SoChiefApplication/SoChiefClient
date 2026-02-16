package fr.vlegall.sochief.client.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiConfig(
    val baseUrl: String,
    val apiKey: String
)
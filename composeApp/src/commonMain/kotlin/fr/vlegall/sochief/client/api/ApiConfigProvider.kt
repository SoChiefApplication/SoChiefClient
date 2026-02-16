package fr.vlegall.sochief.client.api

interface ApiConfigProvider {
    suspend fun get(): ApiConfig?
}
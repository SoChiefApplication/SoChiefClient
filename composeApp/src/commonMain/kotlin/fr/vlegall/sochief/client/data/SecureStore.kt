package fr.vlegall.sochief.client.data

import kotlinx.serialization.KSerializer

expect class SecureStore {
    suspend fun <T> getJson(key: String, serializer: KSerializer<T>): T?
    suspend fun <T> putJson(key: String, serializer: KSerializer<T>, value: T)
    suspend fun remove(key: String)
}
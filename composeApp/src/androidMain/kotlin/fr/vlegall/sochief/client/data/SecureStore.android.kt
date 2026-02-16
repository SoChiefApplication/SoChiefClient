package fr.vlegall.sochief.client.data

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@Suppress("DEPRECATION")
actual class SecureStore(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    actual suspend fun <T> getJson(key: String, serializer: KSerializer<T>): T? =
        withContext(Dispatchers.IO) {
            val raw = prefs.getString(key, null) ?: return@withContext null
            runCatching { json.decodeFromString(serializer, raw) }.getOrNull()
        }

    actual suspend fun <T> putJson(key: String, serializer: KSerializer<T>, value: T) =
        withContext(Dispatchers.IO) {
            val raw = json.encodeToString(serializer, value)
            prefs.edit { putString(key, raw) }
        }

    actual suspend fun remove(key: String) =
        withContext(Dispatchers.IO) { prefs.edit { remove(key) } }
}
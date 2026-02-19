package fr.vlegall.sochief.client.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

actual class SecureStore(
    private val appDir: Path,
    private val keystorePassword: CharArray
) {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    private val ksPath = appDir.resolve("keystore.p12")
    private val dataDir = appDir.resolve("data").also { it.createDirectories() }

    private val keyAlias = "sochief_aes"

    private fun loadOrCreateKey(): SecretKey {
        val ks = KeyStore.getInstance("PKCS12")
        if (ksPath.exists()) {
            ksPath.inputStream().use { ks.load(it, keystorePassword) }
        } else {
            ks.load(null, null)
        }

        val existing = (ks.getKey(keyAlias, keystorePassword) as? SecretKey)
        if (existing != null) return existing

        val gen = KeyGenerator.getInstance("AES")
        gen.init(256)
        val key = gen.generateKey()

        ks.setEntry(
            keyAlias,
            KeyStore.SecretKeyEntry(key),
            KeyStore.PasswordProtection(keystorePassword)
        )
        ksPath.outputStream().use { ks.store(it, keystorePassword) }
        return key
    }

    actual suspend fun <T> getJson(key: String, serializer: KSerializer<T>): T? =
        withContext(Dispatchers.IO) {
            val file = dataDir.resolve("$key.enc")
            if (!Files.exists(file)) return@withContext null

            val secretKey = loadOrCreateKey()
            val bytes = Files.readAllBytes(file)

            if (bytes.size < 12 + 1) return@withContext null
            val iv = bytes.copyOfRange(0, 12)
            val cipherText = bytes.copyOfRange(12, bytes.size)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            val plain = cipher.doFinal(cipherText)
            val raw = plain.toString(Charsets.UTF_8)

            runCatching { json.decodeFromString(serializer, raw) }.getOrNull()
        }

    actual suspend fun <T> putJson(key: String, serializer: KSerializer<T>, value: T): Unit =
        withContext(Dispatchers.IO) {
            val file = dataDir.resolve("$key.enc")
            val secretKey = loadOrCreateKey()
            val raw = json.encodeToString(serializer, value).toByteArray(Charsets.UTF_8)

            val iv = ByteArray(12).also { java.security.SecureRandom().nextBytes(it) }
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            val cipherText = cipher.doFinal(raw)

            run { Files.write(file, iv + cipherText) }
        }

    actual suspend fun remove(key: String): Unit =
        withContext(Dispatchers.IO) {
            val file = dataDir.resolve("$key.enc")
            run { Files.deleteIfExists(file) }
        }
}
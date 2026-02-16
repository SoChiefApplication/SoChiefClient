package fr.vlegall.sochief.client

import android.content.Context
import fr.vlegall.sochief.client.api.ApiConfigRepository
import fr.vlegall.sochief.client.data.SecureStore

object AppContainer {
    lateinit var apiConfigRepository: ApiConfigRepository
        private set

    fun init(appContext: Context) {
        val ctx = appContext.applicationContext
        apiConfigRepository = ApiConfigRepository(SecureStore(ctx))
    }
}

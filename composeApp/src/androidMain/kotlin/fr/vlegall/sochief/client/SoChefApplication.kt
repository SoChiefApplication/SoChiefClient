package fr.vlegall.sochief.client

import android.app.Application
import fr.vlegall.sochief.client.data.PlatformConfig

class SoChefApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialiser la configuration de l'API
        PlatformConfig.initialize(this)
    }
}
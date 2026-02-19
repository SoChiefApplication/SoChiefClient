package fr.vlegall.sochief.client

import android.app.Application

class SoChefApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
    }
}
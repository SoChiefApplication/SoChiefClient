package fr.vlegall.sochief.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.vlegall.sochief.client.configuration.AppDependencies
import fr.vlegall.sochief.client.data.SecureStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(AppDependencies(SecureStore(applicationContext)))
        }
    }
}
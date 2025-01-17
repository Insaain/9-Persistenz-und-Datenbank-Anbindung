package de.hhn.a9persistenzunddatenbank_anbindung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.hhn.a9persistenzunddatenbank_anbindung.ui.screen.DashboardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreen(context = this)
        }
    }
}
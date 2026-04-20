package com.forge.learn

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.forge.learn.navigation.AppNavigation
import com.forge.learn.ui.theme.Learn2SpeakANewLanguageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Install splash screen
        val splashScreen = installSplashScreen()

        // Keep splash screen for at least 2 seconds
        splashScreen.setKeepOnScreenCondition { true }

        // Load resources while splash screen is displayed
        loadResources()

        // Hide splash screen after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
                                                        splashScreen.setKeepOnScreenCondition { false }
                                                    }, 1000)

        enableEdgeToEdge()
        setContent {
            Learn2SpeakANewLanguageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(innerPadding)
                }
            }
        }

    }

    private fun loadResources() {
        initTranslator()
        loadData(this)
        loadPreferences(this)
    }

    override fun onPause() {
        super.onPause()
        savePreferences(this)
    }

    override fun onStop() {
        super.onStop()
        savePreferences(this)
    }
}
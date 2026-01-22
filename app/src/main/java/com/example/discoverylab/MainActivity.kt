package com.example.discoverylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.discoverylab.content.periodictable.PeriodicTableScreen
import com.example.discoverylab.content.solarsystem.SolarSystemScreen
import com.example.discoverylab.core.ui.theme.DiscoveryLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiscoveryLabTheme {
                var currentModule by remember { mutableStateOf<ContentModule?>(null) }

                // Handle back navigation
                BackHandler(enabled = currentModule != null) {
                    currentModule = null
                }

                AnimatedContent(
                    targetState = currentModule,
                    transitionSpec = {
                        if (targetState != null) {
                            // Entering a module
                            (fadeIn() + slideInHorizontally { it / 3 }) togetherWith
                                (fadeOut() + slideOutHorizontally { -it / 3 })
                        } else {
                            // Returning to home
                            (fadeIn() + slideInHorizontally { -it / 3 }) togetherWith
                                (fadeOut() + slideOutHorizontally { it / 3 })
                        }
                    },
                    label = "navigation"
                ) { module ->
                    when (module) {
                        null -> HomeScreen(
                            onModuleSelected = { currentModule = it }
                        )
                        ContentModule.PERIODIC_TABLE -> PeriodicTableScreen()
                        ContentModule.SOLAR_SYSTEM -> SolarSystemScreen()
                    }
                }
            }
        }
    }
}

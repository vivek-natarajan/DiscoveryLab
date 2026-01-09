package com.example.discoverylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.discoverylab.content.periodictable.PeriodicTableScreen
import com.example.discoverylab.core.ui.theme.DiscoveryLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiscoveryLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PeriodicTableScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

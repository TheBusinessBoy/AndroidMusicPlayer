package com.example.androidmusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.androidmusicplayer.ui.screens.*
import com.example.androidmusicplayer.ui.theme.AndroidMusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidMusicPlayerTheme {
                MusicPlayerApp()
            }
        }
    }
}

@Composable
fun MusicPlayerApp() {
    var currentScreen by remember { mutableStateOf("player") }
    
    when (currentScreen) {
        "player" -> PlayerScreen()
        "library" -> LibraryScreen()
        "equalizer" -> EqualizerScreen()
        "lyrics" -> LyricsScreen()
        else -> PlayerScreen()
    }
    
    // For demonstration purposes, you can add navigation logic here
    // In a real app, you would use Navigation Compose or similar
}


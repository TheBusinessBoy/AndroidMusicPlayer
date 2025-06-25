package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

data class LyricLine(
    val text: String,
    val timestamp: Int // in seconds
)

@Composable
fun LyricsScreen() {
    var currentTime by remember { mutableStateOf(83) } // 01:23
    var totalTime by remember { mutableStateOf(223) } // 03:43
    var progress by remember { mutableStateOf(currentTime.toFloat() / totalTime.toFloat()) }
    var isPlaying by remember { mutableStateOf(true) }
    
    val lyrics = listOf(
        LyricLine("If you are interested", 10),
        LyricLine("this situation can change", 15),
        LyricLine("changed my life and all my goals", 20),
        LyricLine("My heart was blinded", 25),
        LyricLine("I know you well I know your smell", 30),
        LyricLine("Goodbye my love", 35),
        LyricLine("Took your soul out into the night", 40),
        LyricLine("My heart was blinded", 45),
        LyricLine("I know you well I know your smell", 50),
        LyricLine("If you are interested", 55),
        LyricLine("You have been the one for me", 60)
    )
    
    // Find current lyric line
    val currentLyricIndex = lyrics.indexOfLast { it.timestamp <= currentTime }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.clickable { }
                )
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bohemian Rhapsody",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Queen",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                
                Row {
                    Icon(
                        Icons.Default.Equalizer,
                        contentDescription = "Equalizer",
                        tint = Color.White,
                        modifier = Modifier.clickable { }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visualizer with album art (smaller version)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Visualizer rings
                AudioVisualizerSmall(
                    modifier = Modifier.fillMaxSize(),
                    isPlaying = isPlaying
                )
                
                // Album art
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = "Album Art",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Song title in lyrics
            Text(
                text = "Protegeme con tu cariño",
                color = Color.Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(
                text = "Enciendeme con tu fuego",
                color = Color.Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lyrics list
            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(lyrics) { index, lyric ->
                    Text(
                        text = lyric.text,
                        color = if (index == currentLyricIndex) Color.Yellow else Color.Gray,
                        fontSize = if (index == currentLyricIndex) 18.sp else 16.sp,
                        fontWeight = if (index == currentLyricIndex) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = progress,
                    onValueChange = { 
                        progress = it
                        currentTime = (it * totalTime).toInt()
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Yellow,
                        activeTrackColor = Color.Yellow,
                        inactiveTrackColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentTime),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = formatTime(totalTime),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { }
                )
                
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { }
                )
                
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Yellow)
                        .clickable { isPlaying = !isPlaying },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { }
                )
                
                Icon(
                    Icons.Default.Repeat,
                    contentDescription = "Repeat",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red,
                        modifier = Modifier.clickable { }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "8/689",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                
                Icon(
                    Icons.Default.QueueMusic,
                    contentDescription = "Queue",
                    tint = Color.White,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Composable
fun AudioVisualizerSmall(
    modifier: Modifier = Modifier,
    isPlaying: Boolean
) {
    var animationProgress by remember { mutableStateOf(0f) }
    
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (isPlaying) {
                animationProgress = (animationProgress + 0.1f) % 1f
                kotlinx.coroutines.delay(50)
            }
        }
    }
    
    Canvas(modifier = modifier) {
        drawVisualizerRingsSmall(animationProgress)
    }
}

private fun DrawScope.drawVisualizerRingsSmall(progress: Float) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val maxRadius = size.minDimension / 2
    
    // Draw single ring with red/pink color
    val radius = maxRadius * 0.9f
    val strokeWidth = 2.dp.toPx()
    
    // Draw animated segments
    for (i in 0 until 40) {
        val angle = (i * 9f + progress * 360f) % 360f
        val startAngle = angle
        val sweepAngle = 4f
        
        val alpha = (sin(progress * 2 * Math.PI + i * 0.15) + 1) / 2
        
        drawArc(
            color = Color.Red.copy(alpha = alpha.toFloat()),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            topLeft = androidx.compose.ui.geometry.Offset(
                centerX - radius,
                centerY - radius
            )
        )
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}


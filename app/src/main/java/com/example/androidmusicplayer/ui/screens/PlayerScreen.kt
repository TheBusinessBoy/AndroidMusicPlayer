package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PlayerScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(83) } // 01:23
    var totalTime by remember { mutableStateOf(223) } // 03:43
    var progress by remember { mutableStateOf(currentTime.toFloat() / totalTime.toFloat()) }

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Spacer(modifier = Modifier.height(32.dp))

            // Visualizer with album art
            Box(
                modifier = Modifier.size(280.dp),
                contentAlignment = Alignment.Center
            ) {
                // Visualizer rings
                AudioVisualizer(
                    modifier = Modifier.fillMaxSize(),
                    isPlaying = isPlaying
                )
                
                // Album art
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = "Album Art",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Lyrics button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
                
                Text(
                    text = "Click to find lyrics",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { }
                )
                
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Progress bar
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = progress,
                    onValueChange = { 
                        progress = it
                        currentTime = (it * totalTime).toInt()
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Cyan,
                        activeTrackColor = Color.Cyan,
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

            Spacer(modifier = Modifier.height(24.dp))

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
                        .background(Color.Cyan)
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
fun AudioVisualizer(
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
        drawVisualizerRings(animationProgress)
    }
}

private fun DrawScope.drawVisualizerRings(progress: Float) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val maxRadius = size.minDimension / 2
    
    // Draw multiple rings with different colors and animations
    val colors = listOf(
        Color.Cyan,
        Color.Yellow,
        Color.Magenta,
        Color.Green
    )
    
    colors.forEachIndexed { index, color ->
        val radius = maxRadius * (0.7f + index * 0.1f)
        val strokeWidth = 3.dp.toPx()
        
        // Draw animated segments
        for (i in 0 until 60) {
            val angle = (i * 6f + progress * 360f) % 360f
            val startAngle = angle
            val sweepAngle = 3f
            
            val alpha = (sin(progress * 2 * Math.PI + i * 0.1) + 1) / 2
            
            drawArc(
                color = color.copy(alpha = alpha.toFloat()),
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
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}


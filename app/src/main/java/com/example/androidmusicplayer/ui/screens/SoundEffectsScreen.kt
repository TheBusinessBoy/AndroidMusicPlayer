package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SoundEffectsScreen() {
    var smartSoundEnabled by remember { mutableStateOf(true) }
    var volumeLevel by remember { mutableStateOf(0.7f) }
    
    // 3D Sound Effects states
    var electronicTubeEnabled by remember { mutableStateOf(false) }
    var rotate3DEnabled by remember { mutableStateOf(false) }
    var toneLowEnabled by remember { mutableStateOf(false) }
    var surroundSoundEnabled by remember { mutableStateOf(false) }
    var magicSoundEnabled by remember { mutableStateOf(false) }
    var liveTrebleEnabled by remember { mutableStateOf(false) }

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
                
                Text(
                    text = "Sound Effect",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.width(24.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Smart Sound section
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF2A2A3E),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Currently used",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        
                        Switch(
                            checked = smartSoundEnabled,
                            onCheckedChange = { smartSoundEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.Yellow,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                    
                    Text(
                        text = "Smart sound",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Volume control
                    Text(
                        text = "Volume",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Slider(
                            value = volumeLevel,
                            onValueChange = { volumeLevel = it },
                            colors = SliderDefaults.colors(
                                thumbColor = Color.White,
                                activeTrackColor = Color.Yellow,
                                inactiveTrackColor = Color.Gray
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        
                        Button(
                            onClick = { /* Apply boost */ },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Yellow
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Boost",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sound effects title
            Text(
                text = "Sound effect",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sound effects list
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoundEffectItem(
                    icon = Icons.Default.GraphicEq,
                    title = "Electronic tube",
                    isEnabled = electronicTubeEnabled,
                    onToggle = { electronicTubeEnabled = it }
                )
                
                SoundEffectItem(
                    icon = Icons.Default.ThreeDRotation,
                    title = "3D rotate",
                    isEnabled = rotate3DEnabled,
                    onToggle = { rotate3DEnabled = it }
                )
                
                SoundEffectItem(
                    icon = Icons.Default.VolumeDown,
                    title = "Tone low",
                    isEnabled = toneLowEnabled,
                    onToggle = { toneLowEnabled = it }
                )
                
                SoundEffectItem(
                    icon = Icons.Default.SurroundSound,
                    title = "Surround sound",
                    isEnabled = surroundSoundEnabled,
                    onToggle = { surroundSoundEnabled = it }
                )
                
                SoundEffectItem(
                    icon = Icons.Default.AutoAwesome,
                    title = "Magic sound",
                    isEnabled = magicSoundEnabled,
                    onToggle = { magicSoundEnabled = it }
                )
                
                SoundEffectItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Live treble",
                    isEnabled = liveTrebleEnabled,
                    onToggle = { liveTrebleEnabled = it }
                )
            }
        }
    }
}

@Composable
fun SoundEffectItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isEnabled) },
        backgroundColor = if (isEnabled) Color(0xFF3A3A4E) else Color(0xFF2A2A3E),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = if (isEnabled) Color.Yellow else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Button(
                onClick = { onToggle(!isEnabled) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isEnabled) Color.Yellow else Color.Gray
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(width = 60.dp, height = 32.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (isEnabled) "USE" else "USE",
                    color = if (isEnabled) Color.Black else Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EqualizerScreen() {
    var isEqualizerEnabled by remember { mutableStateOf(true) }
    var selectedPreset by remember { mutableStateOf("Custom") }
    var bassLevel by remember { mutableStateOf(0.5f) }
    var virtualizerLevel by remember { mutableStateOf(0.3f) }
    var volumeLevel by remember { mutableStateOf(0.7f) }
    var amplifierLevel by remember { mutableStateOf(0.6f) }
    var leftBalance by remember { mutableStateOf(0.5f) }
    var rightBalance by remember { mutableStateOf(0.5f) }
    var reverbSetting by remember { mutableStateOf("None") }
    
    // Equalizer band values (60Hz, 230Hz, 910Hz, 3.6KHz, 14KHz)
    var bandValues by remember { 
        mutableStateOf(listOf(0.2f, 0.4f, 0.6f, 0.3f, 0.5f))
    }
    
    val presets = listOf("Pop", "Folk", "Rock", "Dance")

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

            // Equalizer section
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
                            text = "EQUALIZER",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Switch(
                            checked = isEqualizerEnabled,
                            onCheckedChange = { isEqualizerEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.Green,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Preset selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedPreset,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        
                        Row {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.Save,
                                contentDescription = "Save",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Equalizer bands
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val frequencies = listOf("60Hz", "230Hz", "910Hz", "3.6KHz", "14KHz")
                        
                        frequencies.forEachIndexed { index, freq ->
                            EqualizerBand(
                                frequency = freq,
                                value = bandValues[index],
                                onValueChange = { newValue ->
                                    bandValues = bandValues.toMutableList().apply {
                                        this[index] = newValue
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Preset buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                presets.forEach { preset ->
                    Button(
                        onClick = { selectedPreset = preset },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = when (preset) {
                                "Pop" -> Color.Yellow
                                "Folk" -> Color.Red
                                "Rock" -> Color.Magenta
                                "Dance" -> Color.Green
                                else -> Color.Gray
                            }
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = preset,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Audio controls
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Reverb
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reverb",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = reverbSetting,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = "Arrow",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Bass and Virtualizer knobs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AudioKnob(
                        label = "Bass",
                        value = bassLevel,
                        onValueChange = { bassLevel = it }
                    )
                    
                    AudioKnob(
                        label = "Virtualizer",
                        value = virtualizerLevel,
                        onValueChange = { virtualizerLevel = it }
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Volume slider
                AudioSlider(
                    label = "VOLUME",
                    value = volumeLevel,
                    onValueChange = { volumeLevel = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Amplifier slider
                AudioSlider(
                    label = "AMPLIFIER",
                    value = amplifierLevel,
                    onValueChange = { amplifierLevel = it },
                    enabled = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Sound balance
                Text(
                    text = "SOUND BALANCE",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AudioKnob(
                        label = "Left",
                        value = leftBalance,
                        onValueChange = { leftBalance = it }
                    )
                    
                    AudioKnob(
                        label = "Right",
                        value = rightBalance,
                        onValueChange = { rightBalance = it }
                    )
                }
            }
        }
    }
}

@Composable
fun EqualizerBand(
    frequency: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "15",
            color = Color.White,
            fontSize = 12.sp
        )
        
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = value,
                onValueChange = onValueChange,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Green,
                    inactiveTrackColor = Color.Gray
                ),
                modifier = Modifier
                    .height(120.dp)
                    .width(40.dp)
            )
        }
        
        Text(
            text = "-15",
            color = Color.White,
            fontSize = 12.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = frequency,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

@Composable
fun AudioKnob(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawKnob(value)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun AudioSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    enabled: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            
            if (enabled) {
                Switch(
                    checked = true,
                    onCheckedChange = { },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Green
                    ),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Green,
                inactiveTrackColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun DrawScope.drawKnob(value: Float) {
    val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 2 * 0.8f
    
    // Draw outer ring
    drawCircle(
        color = Color.Gray,
        radius = radius,
        center = center,
        style = Stroke(width = 4.dp.toPx())
    )
    
    // Draw active arc
    val sweepAngle = value * 270f
    drawArc(
        color = Color.Green,
        startAngle = 135f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(width = 4.dp.toPx()),
        size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
        topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius)
    )
    
    // Draw inner circle
    drawCircle(
        color = Color(0xFF4A4A5A),
        radius = radius * 0.7f,
        center = center
    )
    
    // Draw indicator
    val angle = (135f + sweepAngle) * Math.PI / 180f
    val indicatorStart = androidx.compose.ui.geometry.Offset(
        center.x + cos(angle).toFloat() * radius * 0.4f,
        center.y + sin(angle).toFloat() * radius * 0.4f
    )
    val indicatorEnd = androidx.compose.ui.geometry.Offset(
        center.x + cos(angle).toFloat() * radius * 0.6f,
        center.y + sin(angle).toFloat() * radius * 0.6f
    )
    
    drawLine(
        color = Color.White,
        start = indicatorStart,
        end = indicatorEnd,
        strokeWidth = 3.dp.toPx()
    )
}


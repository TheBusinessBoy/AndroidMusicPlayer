package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

data class ConversionTask(
    val id: String,
    val inputFile: String,
    val outputFormat: String,
    val quality: String,
    val progress: Int,
    val status: ConversionStatus
)

enum class ConversionStatus {
    PENDING, CONVERTING, COMPLETED, FAILED, CANCELLED
}

@Composable
fun FileConverterScreen() {
    var selectedInputFile by remember { mutableStateOf<String?>(null) }
    var selectedOutputFormat by remember { mutableStateOf("mp3") }
    var selectedQuality by remember { mutableStateOf("medium") }
    var showFormatDialog by remember { mutableStateOf(false) }
    var showQualityDialog by remember { mutableStateOf(false) }
    var conversionTasks by remember { mutableStateOf(listOf<ConversionTask>()) }
    
    val audioFormats = listOf("mp3", "aac", "flac", "wav", "ogg", "m4a")
    val qualityOptions = listOf("low", "medium", "high", "lossless")

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
                    text = "File Converter",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Icon(
                    Icons.Default.Help,
                    contentDescription = "Help",
                    tint = Color.White,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // File selection section
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF2A2A3E),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Select File to Convert",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Input file selection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Open file picker */ },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Folder,
                            contentDescription = "Select File",
                            tint = Color.Cyan,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Text(
                            text = selectedInputFile ?: "Tap to select audio/video file",
                            color = if (selectedInputFile != null) Color.White else Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Select",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Output format selection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showFormatDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AudioFile,
                            contentDescription = "Output Format",
                            tint = Color.Green,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Output Format",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = selectedOutputFormat.uppercase(),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Select",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Quality selection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showQualityDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.HighQuality,
                            contentDescription = "Quality",
                            tint = Color.Yellow,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Quality",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = selectedQuality.capitalize(),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Select",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Convert button
                    Button(
                        onClick = {
                            selectedInputFile?.let { inputFile ->
                                val newTask = ConversionTask(
                                    id = System.currentTimeMillis().toString(),
                                    inputFile = inputFile,
                                    outputFormat = selectedOutputFormat,
                                    quality = selectedQuality,
                                    progress = 0,
                                    status = ConversionStatus.PENDING
                                )
                                conversionTasks = conversionTasks + newTask
                                // Start conversion here
                            }
                        },
                        enabled = selectedInputFile != null,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Cyan,
                            disabledBackgroundColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Transform,
                            contentDescription = "Convert",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Start Conversion",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Conversion queue
            if (conversionTasks.isNotEmpty()) {
                Text(
                    text = "Conversion Queue",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(conversionTasks) { task ->
                        ConversionTaskItem(
                            task = task,
                            onCancel = { taskId ->
                                conversionTasks = conversionTasks.filter { it.id != taskId }
                            },
                            onRetry = { taskId ->
                                conversionTasks = conversionTasks.map { 
                                    if (it.id == taskId) {
                                        it.copy(status = ConversionStatus.PENDING, progress = 0)
                                    } else it
                                }
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Format selection dialog
    if (showFormatDialog) {
        AlertDialog(
            onDismissRequest = { showFormatDialog = false },
            title = {
                Text(
                    text = "Select Output Format",
                    color = Color.White
                )
            },
            text = {
                LazyColumn {
                    items(audioFormats) { format ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedOutputFormat = format
                                    showFormatDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOutputFormat == format,
                                onClick = {
                                    selectedOutputFormat = format
                                    showFormatDialog = false
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Cyan
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = format.uppercase(),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showFormatDialog = false }
                ) {
                    Text("Cancel", color = Color.Cyan)
                }
            },
            backgroundColor = Color(0xFF2A2A3E)
        )
    }
    
    // Quality selection dialog
    if (showQualityDialog) {
        AlertDialog(
            onDismissRequest = { showQualityDialog = false },
            title = {
                Text(
                    text = "Select Quality",
                    color = Color.White
                )
            },
            text = {
                LazyColumn {
                    items(qualityOptions) { quality ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedQuality = quality
                                    showQualityDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedQuality == quality,
                                onClick = {
                                    selectedQuality = quality
                                    showQualityDialog = false
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Cyan
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = quality.capitalize(),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showQualityDialog = false }
                ) {
                    Text("Cancel", color = Color.Cyan)
                }
            },
            backgroundColor = Color(0xFF2A2A3E)
        )
    }
}

@Composable
fun ConversionTaskItem(
    task: ConversionTask,
    onCancel: (String) -> Unit,
    onRetry: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFF2A2A3E),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.inputFile.substringAfterLast("/"),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = "to ${task.outputFormat.uppercase()} (${task.quality})",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                when (task.status) {
                    ConversionStatus.PENDING -> {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = "Pending",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    ConversionStatus.CONVERTING -> {
                        CircularProgressIndicator(
                            color = Color.Cyan,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    ConversionStatus.COMPLETED -> {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = Color.Green,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    ConversionStatus.FAILED -> {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Failed",
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    ConversionStatus.CANCELLED -> {
                        Icon(
                            Icons.Default.Cancel,
                            contentDescription = "Cancelled",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            if (task.status == ConversionStatus.CONVERTING) {
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = task.progress / 100f,
                    color = Color.Cyan,
                    backgroundColor = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "${task.progress}%",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            if (task.status == ConversionStatus.CONVERTING || task.status == ConversionStatus.PENDING) {
                Spacer(modifier = Modifier.height(8.dp))
                
                TextButton(
                    onClick = { onCancel(task.id) }
                ) {
                    Text("Cancel", color = Color.Red)
                }
            }
            
            if (task.status == ConversionStatus.FAILED) {
                Spacer(modifier = Modifier.height(8.dp))
                
                TextButton(
                    onClick = { onRetry(task.id) }
                ) {
                    Text("Retry", color = Color.Cyan)
                }
            }
        }
    }
}


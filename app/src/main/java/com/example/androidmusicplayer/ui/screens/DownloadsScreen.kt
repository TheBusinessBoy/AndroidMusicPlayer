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

data class DownloadItem(
    val id: Long,
    val title: String,
    val artist: String?,
    val url: String,
    val progress: Int,
    val status: DownloadStatus,
    val filePath: String? = null
)

enum class DownloadStatus {
    PENDING, DOWNLOADING, COMPLETED, FAILED, PAUSED, CANCELLED
}

@Composable
fun DownloadsScreen() {
    var downloadUrl by remember { mutableStateOf("") }
    var showUrlDialog by remember { mutableStateOf(false) }
    var downloads by remember { 
        mutableStateOf(listOf<DownloadItem>()) 
    }
    
    // Sample downloads for demonstration
    LaunchedEffect(Unit) {
        downloads = listOf(
            DownloadItem(
                id = 1,
                title = "Bohemian Rhapsody",
                artist = "Queen",
                url = "https://example.com/bohemian-rhapsody",
                progress = 100,
                status = DownloadStatus.COMPLETED,
                filePath = "/storage/emulated/0/Music/MusicPlayer/Downloads/bohemian_rhapsody.mp3"
            ),
            DownloadItem(
                id = 2,
                title = "Imagine",
                artist = "John Lennon",
                url = "https://example.com/imagine",
                progress = 65,
                status = DownloadStatus.DOWNLOADING
            ),
            DownloadItem(
                id = 3,
                title = "Hotel California",
                artist = "Eagles",
                url = "https://example.com/hotel-california",
                progress = 0,
                status = DownloadStatus.FAILED
            )
        )
    }

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
                    text = "Downloads",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Download",
                    tint = Color.White,
                    modifier = Modifier.clickable { showUrlDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick download section
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF2A2A3E),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Quick Download",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Download current song button
                    Button(
                        onClick = {
                            // Download currently playing song
                            val newDownload = DownloadItem(
                                id = System.currentTimeMillis(),
                                title = "Currently Playing Song",
                                artist = "Current Artist",
                                url = "current://playing",
                                progress = 0,
                                status = DownloadStatus.PENDING
                            )
                            downloads = downloads + newDownload
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Cyan
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Download Current Song",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Download from URL button
                    OutlinedButton(
                        onClick = { showUrlDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Link,
                            contentDescription = "URL",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Download from URL",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Downloads list
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Downloads (${downloads.size})",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Row {
                    Icon(
                        Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (downloads.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CloudDownload,
                            contentDescription = "No Downloads",
                            tint = Color.Gray,
                            modifier = Modifier.size(64.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "No downloads yet",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        
                        Text(
                            text = "Start downloading your favorite music",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(downloads) { download ->
                        DownloadItemCard(
                            download = download,
                            onPause = { id ->
                                downloads = downloads.map { 
                                    if (it.id == id && it.status == DownloadStatus.DOWNLOADING) {
                                        it.copy(status = DownloadStatus.PAUSED)
                                    } else it
                                }
                            },
                            onResume = { id ->
                                downloads = downloads.map { 
                                    if (it.id == id && it.status == DownloadStatus.PAUSED) {
                                        it.copy(status = DownloadStatus.DOWNLOADING)
                                    } else it
                                }
                            },
                            onCancel = { id ->
                                downloads = downloads.filter { it.id != id }
                            },
                            onRetry = { id ->
                                downloads = downloads.map { 
                                    if (it.id == id && it.status == DownloadStatus.FAILED) {
                                        it.copy(status = DownloadStatus.PENDING, progress = 0)
                                    } else it
                                }
                            },
                            onPlay = { download ->
                                // Play the downloaded file
                            }
                        )
                    }
                }
            }
        }
    }
    
    // URL input dialog
    if (showUrlDialog) {
        AlertDialog(
            onDismissRequest = { showUrlDialog = false },
            title = {
                Text(
                    text = "Download from URL",
                    color = Color.White
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter the URL of the music/video to download:",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = downloadUrl,
                        onValueChange = { downloadUrl = it },
                        placeholder = {
                            Text(
                                text = "https://...",
                                color = Color.Gray
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.Cyan,
                            focusedBorderColor = Color.Cyan,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (downloadUrl.isNotBlank()) {
                            val newDownload = DownloadItem(
                                id = System.currentTimeMillis(),
                                title = "Unknown Title",
                                artist = "Unknown Artist",
                                url = downloadUrl,
                                progress = 0,
                                status = DownloadStatus.PENDING
                            )
                            downloads = downloads + newDownload
                            downloadUrl = ""
                            showUrlDialog = false
                        }
                    }
                ) {
                    Text("Download", color = Color.Cyan)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        downloadUrl = ""
                        showUrlDialog = false 
                    }
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            backgroundColor = Color(0xFF2A2A3E)
        )
    }
}

@Composable
fun DownloadItemCard(
    download: DownloadItem,
    onPause: (Long) -> Unit,
    onResume: (Long) -> Unit,
    onCancel: (Long) -> Unit,
    onRetry: (Long) -> Unit,
    onPlay: (DownloadItem) -> Unit
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
                // Song info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = download.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    download.artist?.let { artist ->
                        Text(
                            text = artist,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    
                    Text(
                        text = when (download.status) {
                            DownloadStatus.PENDING -> "Pending..."
                            DownloadStatus.DOWNLOADING -> "Downloading..."
                            DownloadStatus.COMPLETED -> "Completed"
                            DownloadStatus.FAILED -> "Failed"
                            DownloadStatus.PAUSED -> "Paused"
                            DownloadStatus.CANCELLED -> "Cancelled"
                        },
                        color = when (download.status) {
                            DownloadStatus.COMPLETED -> Color.Green
                            DownloadStatus.FAILED -> Color.Red
                            DownloadStatus.PAUSED -> Color.Yellow
                            DownloadStatus.CANCELLED -> Color.Gray
                            else -> Color.Cyan
                        },
                        fontSize = 12.sp
                    )
                }
                
                // Status icon and actions
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (download.status) {
                        DownloadStatus.DOWNLOADING -> {
                            IconButton(
                                onClick = { onPause(download.id) }
                            ) {
                                Icon(
                                    Icons.Default.Pause,
                                    contentDescription = "Pause",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        DownloadStatus.PAUSED -> {
                            IconButton(
                                onClick = { onResume(download.id) }
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Resume",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        DownloadStatus.COMPLETED -> {
                            IconButton(
                                onClick = { onPlay(download) }
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.Green,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        DownloadStatus.FAILED -> {
                            IconButton(
                                onClick = { onRetry(download.id) }
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = "Retry",
                                    tint = Color.Red,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        else -> {
                            CircularProgressIndicator(
                                color = Color.Cyan,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                    
                    if (download.status != DownloadStatus.COMPLETED) {
                        IconButton(
                            onClick = { onCancel(download.id) }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Cancel",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            
            // Progress bar for active downloads
            if (download.status == DownloadStatus.DOWNLOADING || download.status == DownloadStatus.PAUSED) {
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = download.progress / 100f,
                    color = Color.Cyan,
                    backgroundColor = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "${download.progress}%",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Track(
    val title: String,
    val artist: String,
    val duration: String,
    val albumArt: Int? = null
)

data class Album(
    val title: String,
    val artist: String,
    val trackCount: String,
    val albumArt: Int? = null
)

@Composable
fun LibraryScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("TRACKS", "ALBUMS", "ARTISTS", "FOLDERS", "PLAYLISTS", "GENRES")
    
    // Sample data
    val tracks = listOf(
        Track("Love Story", "Jeff Blunt", "02:18"),
        Track("Hey Soul Sister", "Queen", "3:50"),
        Track("Infinite Vitality", "Billy Pointer", ""),
        Track("Bohemian Rhapsody", "Queen", "02:03"),
        Track("Kiss The Rain", "James Bay", "04:50"),
        Track("Love Somebody", "Eagles", "03:50"),
        Track("Night Changes", "Red Stewart", "3:02"),
        Track("Infinite Vitality", "Billy Pointer", "")
    )
    
    val albums = listOf(
        Album("Gypsy Soul", "7 Tracks", ""),
        Album("Angela", "11 Tracks", ""),
        Album("Gypsy Soul", "7 Tracks", ""),
        Album("Gypsy Soul", "7 Tracks", "")
    )

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
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
                
                Text(
                    text = "Music Player",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Row {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
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

            // Tab row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color.Cyan
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) Color.Cyan else Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content based on selected tab
            when (selectedTab) {
                0 -> TracksContent(tracks)
                1 -> AlbumsContent(albums)
                else -> TracksContent(tracks) // Default to tracks for other tabs
            }
        }
    }
}

@Composable
fun TracksContent(tracks: List<Track>) {
    Column {
        // Shuffle all button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = Color.Cyan,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Shuffle all (68)",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Default.Sort,
                contentDescription = "Sort",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

        // Track list
        LazyColumn {
            items(tracks) { track ->
                TrackItem(track = track)
            }
        }
    }
}

@Composable
fun AlbumsContent(albums: List<Album>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(albums.chunked(2)) { albumPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                albumPair.forEach { album ->
                    AlbumItem(
                        album = album,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill remaining space if odd number of albums
                if (albumPair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun TrackItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Album art placeholder
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.MusicNote,
                contentDescription = "Album Art",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = track.artist,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        
        if (track.duration.isNotEmpty()) {
            Text(
                text = track.duration,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "More",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable { }
        )
    }
}

@Composable
fun AlbumItem(album: Album, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clickable { }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Album art
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B),
                            Color(0xFF4ECDC4),
                            Color(0xFF45B7D1)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Album,
                contentDescription = "Album Art",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = album.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = album.trackCount,
            color = Color.Gray,
            fontSize = 12.sp
        )
        
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "More",
            tint = Color.Gray,
            modifier = Modifier
                .size(16.dp)
                .clickable { }
        )
    }
}


package com.example.androidmusicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidmusicplayer.ui.theme.AppTheme
import com.example.androidmusicplayer.ui.theme.ThemeColors
import com.example.androidmusicplayer.ui.theme.getThemeGradientColors

@Composable
fun ThemeSettingsScreen() {
    var selectedTheme by remember { mutableStateOf(AppTheme.DEFAULT) }
    var autoThemeEnabled by remember { mutableStateOf(false) }
    var dynamicColorsEnabled by remember { mutableStateOf(false) }
    
    val themes = listOf(
        ThemeOption(AppTheme.DEFAULT, "Default", "Purple & Blue", ThemeColors.DarkPrimary),
        ThemeOption(AppTheme.ORANGE, "Sunset", "Orange & Amber", ThemeColors.OrangePrimary),
        ThemeOption(AppTheme.GREEN, "Nature", "Green & Forest", ThemeColors.GreenPrimary),
        ThemeOption(AppTheme.BLUE, "Ocean", "Blue & Cyan", ThemeColors.BluePrimary),
        ThemeOption(AppTheme.RED, "Fire", "Red & Crimson", ThemeColors.RedPrimary),
        ThemeOption(AppTheme.PINK, "Blossom", "Pink & Rose", ThemeColors.PinkPrimary)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = getThemeGradientColors(selectedTheme)
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
                    text = "Themes",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Icon(
                    Icons.Default.Palette,
                    contentDescription = "Palette",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme preview section
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF2A2A3E),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Preview",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Mini player preview
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = getThemeGradientColors(selectedTheme)
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Mini visualizer
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.MusicNote,
                                    contentDescription = "Music",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Sample Song",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Text(
                                text = "Sample Artist",
                                color = Color.Gray,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Theme options
            Text(
                text = "Choose Theme",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(themes) { theme ->
                    ThemeOptionCard(
                        theme = theme,
                        isSelected = selectedTheme == theme.type,
                        onSelect = { selectedTheme = theme.type }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Additional settings
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF2A2A3E),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Theme Settings",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Auto theme toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Auto Theme",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Change theme based on time of day",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        
                        Switch(
                            checked = autoThemeEnabled,
                            onCheckedChange = { autoThemeEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = when (selectedTheme) {
                                    AppTheme.DEFAULT -> Color.Cyan
                                    AppTheme.ORANGE -> ThemeColors.OrangePrimary
                                    AppTheme.GREEN -> ThemeColors.GreenPrimary
                                    AppTheme.BLUE -> ThemeColors.BluePrimary
                                    AppTheme.RED -> ThemeColors.RedPrimary
                                    AppTheme.PINK -> ThemeColors.PinkPrimary
                                },
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Dynamic colors toggle (Android 12+)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Dynamic Colors",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Use system wallpaper colors (Android 12+)",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        
                        Switch(
                            checked = dynamicColorsEnabled,
                            onCheckedChange = { dynamicColorsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = when (selectedTheme) {
                                    AppTheme.DEFAULT -> Color.Cyan
                                    AppTheme.ORANGE -> ThemeColors.OrangePrimary
                                    AppTheme.GREEN -> ThemeColors.GreenPrimary
                                    AppTheme.BLUE -> ThemeColors.BluePrimary
                                    AppTheme.RED -> ThemeColors.RedPrimary
                                    AppTheme.PINK -> ThemeColors.PinkPrimary
                                },
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                }
            }
        }
    }
}

data class ThemeOption(
    val type: AppTheme,
    val name: String,
    val description: String,
    val primaryColor: Color
)

@Composable
fun ThemeOptionCard(
    theme: ThemeOption,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = theme.primaryColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        backgroundColor = Color(0xFF2A2A3E),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color preview
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(80.dp)
            ) {
                items(getThemeGradientColors(theme.type)) { color ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Theme info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = theme.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = theme.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            
            // Selection indicator
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = theme.primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}


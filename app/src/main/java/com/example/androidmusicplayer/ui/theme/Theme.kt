package com.example.androidmusicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Theme Colors
object ThemeColors {
    // Default Dark Theme (Purple/Blue)
    val DarkPrimary = Color(0xFF6200EE)
    val DarkPrimaryVariant = Color(0xFF3700B3)
    val DarkSecondary = Color(0xFF03DAC5)
    val DarkBackground = Color(0xFF121212)
    val DarkSurface = Color(0xFF1E1E1E)
    val DarkOnPrimary = Color.White
    val DarkOnSecondary = Color.Black
    val DarkOnBackground = Color.White
    val DarkOnSurface = Color.White
    
    // Orange Theme
    val OrangePrimary = Color(0xFFFF6B35)
    val OrangePrimaryVariant = Color(0xFFE55100)
    val OrangeSecondary = Color(0xFFFFAB40)
    val OrangeBackground = Color(0xFF1A1A2E)
    val OrangeSurface = Color(0xFF2A2A3E)
    
    // Green Theme
    val GreenPrimary = Color(0xFF4CAF50)
    val GreenPrimaryVariant = Color(0xFF2E7D32)
    val GreenSecondary = Color(0xFF8BC34A)
    val GreenBackground = Color(0xFF0D1B0F)
    val GreenSurface = Color(0xFF1B2F1D)
    
    // Blue Theme
    val BluePrimary = Color(0xFF2196F3)
    val BluePrimaryVariant = Color(0xFF1565C0)
    val BlueSecondary = Color(0xFF03DAC5)
    val BlueBackground = Color(0xFF0A1929)
    val BlueSurface = Color(0xFF1E293B)
    
    // Red Theme
    val RedPrimary = Color(0xFFF44336)
    val RedPrimaryVariant = Color(0xFFD32F2F)
    val RedSecondary = Color(0xFFFF5722)
    val RedBackground = Color(0xFF1A0A0A)
    val RedSurface = Color(0xFF2A1A1A)
    
    // Pink Theme
    val PinkPrimary = Color(0xFFE91E63)
    val PinkPrimaryVariant = Color(0xFFC2185B)
    val PinkSecondary = Color(0xFFFF4081)
    val PinkBackground = Color(0xFF1A0A14)
    val PinkSurface = Color(0xFF2A1A24)
}

enum class AppTheme {
    DEFAULT,
    ORANGE,
    GREEN,
    BLUE,
    RED,
    PINK
}

@Composable
fun AndroidMusicPlayerTheme(
    theme: AppTheme = AppTheme.DEFAULT,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        AppTheme.DEFAULT -> if (darkTheme) {
            darkColors(
                primary = ThemeColors.DarkPrimary,
                primaryVariant = ThemeColors.DarkPrimaryVariant,
                secondary = ThemeColors.DarkSecondary,
                background = ThemeColors.DarkBackground,
                surface = ThemeColors.DarkSurface,
                onPrimary = ThemeColors.DarkOnPrimary,
                onSecondary = ThemeColors.DarkOnSecondary,
                onBackground = ThemeColors.DarkOnBackground,
                onSurface = ThemeColors.DarkOnSurface
            )
        } else {
            lightColors(
                primary = ThemeColors.DarkPrimary,
                primaryVariant = ThemeColors.DarkPrimaryVariant,
                secondary = ThemeColors.DarkSecondary
            )
        }
        
        AppTheme.ORANGE -> darkColors(
            primary = ThemeColors.OrangePrimary,
            primaryVariant = ThemeColors.OrangePrimaryVariant,
            secondary = ThemeColors.OrangeSecondary,
            background = ThemeColors.OrangeBackground,
            surface = ThemeColors.OrangeSurface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        
        AppTheme.GREEN -> darkColors(
            primary = ThemeColors.GreenPrimary,
            primaryVariant = ThemeColors.GreenPrimaryVariant,
            secondary = ThemeColors.GreenSecondary,
            background = ThemeColors.GreenBackground,
            surface = ThemeColors.GreenSurface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        
        AppTheme.BLUE -> darkColors(
            primary = ThemeColors.BluePrimary,
            primaryVariant = ThemeColors.BluePrimaryVariant,
            secondary = ThemeColors.BlueSecondary,
            background = ThemeColors.BlueBackground,
            surface = ThemeColors.BlueSurface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        
        AppTheme.RED -> darkColors(
            primary = ThemeColors.RedPrimary,
            primaryVariant = ThemeColors.RedPrimaryVariant,
            secondary = ThemeColors.RedSecondary,
            background = ThemeColors.RedBackground,
            surface = ThemeColors.RedSurface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        
        AppTheme.PINK -> darkColors(
            primary = ThemeColors.PinkPrimary,
            primaryVariant = ThemeColors.PinkPrimaryVariant,
            secondary = ThemeColors.PinkSecondary,
            background = ThemeColors.PinkBackground,
            surface = ThemeColors.PinkSurface,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

// Theme-specific gradient colors
@Composable
fun getThemeGradientColors(theme: AppTheme): List<Color> {
    return when (theme) {
        AppTheme.DEFAULT -> listOf(
            Color(0xFF1A1A2E),
            Color(0xFF16213E),
            Color(0xFF0F3460)
        )
        AppTheme.ORANGE -> listOf(
            Color(0xFF1A1A2E),
            Color(0xFF2A1810),
            Color(0xFF3D2414)
        )
        AppTheme.GREEN -> listOf(
            Color(0xFF0D1B0F),
            Color(0xFF1B2F1D),
            Color(0xFF2E5233)
        )
        AppTheme.BLUE -> listOf(
            Color(0xFF0A1929),
            Color(0xFF1E293B),
            Color(0xFF334155)
        )
        AppTheme.RED -> listOf(
            Color(0xFF1A0A0A),
            Color(0xFF2A1A1A),
            Color(0xFF3D2A2A)
        )
        AppTheme.PINK -> listOf(
            Color(0xFF1A0A14),
            Color(0xFF2A1A24),
            Color(0xFF3D2A34)
        )
    }
}

// Theme-specific accent colors
@Composable
fun getThemeAccentColor(theme: AppTheme): Color {
    return when (theme) {
        AppTheme.DEFAULT -> Color.Cyan
        AppTheme.ORANGE -> ThemeColors.OrangePrimary
        AppTheme.GREEN -> ThemeColors.GreenPrimary
        AppTheme.BLUE -> ThemeColors.BluePrimary
        AppTheme.RED -> ThemeColors.RedPrimary
        AppTheme.PINK -> ThemeColors.PinkPrimary
    }
}


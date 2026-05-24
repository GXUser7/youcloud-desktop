package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ThemePreset(
    val name: String,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

val ThemePresets = listOf(
    ThemePreset("Indigo Lavender", Color(0xFF7F52FF), Color(0xFFB092FF), Color(0xFFFF85C1)),
    ThemePreset("Ocean Teal", Color(0xFF00B4D8), Color(0xFF90E0EF), Color(0xFF0077B6)),
    ThemePreset("Forest Mint", Color(0xFF00B48A), Color(0xFF57D9A3), Color(0xFF80DEEA)),
    ThemePreset("Sakura Blossom", Color(0xFFE63956), Color(0xFFFF758F), Color(0xFFFFB3C1)),
    ThemePreset("Nordic Slate", Color(0xFF7A869A), Color(0xFFB3BAC5), Color(0xFFDFE1E6)),
    ThemePreset("Cyberpunk Neon", Color(0xFFBD00FF), Color(0xFFFF007F), Color(0xFF00F0FF)),
    ThemePreset("Sunset Gold", Color(0xFFFF5E36), Color(0xFFFFAE19), Color(0xFFFFE0B2)),
    ThemePreset("Crimson Velvet", Color(0xFFD64264), Color(0xFFFF758F), Color(0xFFFFD700)),
    ThemePreset("Midnight Blue", Color(0xFF3B82F6), Color(0xFF6366F1), Color(0xFF1E3A8A)),
    ThemePreset("Emerald Rose", Color(0xFF047857), Color(0xFFFB7185), Color(0xFF0D9488)),
    ThemePreset("Expressive Violet", Color(0xFFCBBEFF), Color(0xFFCCBEFF), Color(0xFFD64264))
)

private fun Color.darken(factor: Float = 0.22f): Color {
    return Color(
        red = (this.red * factor).coerceIn(0f, 1f),
        green = (this.green * factor).coerceIn(0f, 1f),
        blue = (this.blue * factor).coerceIn(0f, 1f),
        alpha = this.alpha
    )
}

fun getDarkColorScheme(preset: ThemePreset) = darkColorScheme(
    primary = preset.primary,
    onPrimary = preset.primary.darken(0.22f),
    primaryContainer = preset.primary.copy(alpha = 0.25f),
    onPrimaryContainer = preset.primary,
    secondary = preset.secondary,
    onSecondary = preset.secondary.darken(0.22f),
    secondaryContainer = Color(0xFF201F21),
    onSecondaryContainer = preset.secondary,
    tertiary = preset.tertiary,
    onTertiary = preset.tertiary.darken(0.22f),
    background = Color(0xFF141315),
    onBackground = Color(0xFFE6E1E4),
    surface = Color(0xFF141315),
    onSurface = Color(0xFFE6E1E4),
    surfaceVariant = Color(0xFF201F21),
    onSurfaceVariant = Color(0xFFCAC4D7),
    outline = Color(0xFF484554)
)

fun getLightColorScheme(preset: ThemePreset) = lightColorScheme(
    primary = preset.primary,
    onPrimary = Color.White,
    primaryContainer = preset.primary.copy(alpha = 0.15f),
    onPrimaryContainer = preset.primary,
    secondary = preset.secondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF5F3FA),
    onSecondaryContainer = preset.secondary,
    tertiary = preset.tertiary,
    onTertiary = Color.White,
    background = Color(0xFFFAF8FF),
    onBackground = Color(0xFF1B1B1F),
    surface = Color(0xFFFCFAFF),
    onSurface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFFE7E1E9),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E)
)

@Composable
fun MyApplicationTheme(
    presetName: String = "Indigo Lavender",
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val preset = ThemePresets.firstOrNull { it.name == presetName } ?: ThemePresets[0]
    val colorScheme = if (darkTheme) getDarkColorScheme(preset) else getLightColorScheme(preset)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

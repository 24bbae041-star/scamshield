package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DeepPurple,
    onPrimary = Color.White,
    primaryContainer = LavenderContainer,
    onPrimaryContainer = OnLavenderContainer,
    secondary = MintDark,
    onSecondary = Color.White,
    secondaryContainer = MintContainer,
    onSecondaryContainer = DarkInk,
    tertiary = ButterYellowDark,
    onTertiary = Color.White,
    tertiaryContainer = ButterYellowContainer,
    onTertiaryContainer = DarkInk,
    background = WarmCream,
    onBackground = DarkInk,
    surface = WarmCard,
    onSurface = DarkInk,
    surfaceVariant = Color(0xFFF1ECFC),
    onSurfaceVariant = SubtitleInk,
    outline = BorderStroke,
    error = WarningRose,
    onError = Color.White,
    errorContainer = WarningRoseContainer,
    onErrorContainer = WarningRoseDark
)

private val HighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF381F8C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD3C2FF),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF135A3E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB4EBD2),
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFF6B5100),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDE80),
    onTertiaryContainer = Color.Black,
    background = Color(0xFFFFFBF7),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE4DCF9),
    onSurfaceVariant = Color.Black,
    outline = Color(0xFF292638),
    error = Color(0xFFBA1A2B),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

private val DarkColorScheme = darkColorScheme(
    primary = Lavender,
    onPrimary = OnLavenderContainer,
    primaryContainer = DeepPurple,
    onPrimaryContainer = LavenderContainer,
    secondary = Mint,
    onSecondary = DarkInk,
    secondaryContainer = MintDark,
    onSecondaryContainer = MintContainer,
    tertiary = ButterYellow,
    onTertiary = DarkInk,
    tertiaryContainer = ButterYellowDark,
    onTertiaryContainer = ButterYellowContainer,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceCard,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    error = WarningRose,
    onError = Color.White,
    errorContainer = WarningRoseDark,
    onErrorContainer = WarningRoseContainer
)

@Composable
fun ScanShieldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = false,
    textScale: SeniorTextScale = SeniorTextScale.NORMAL,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrast -> HighContrastLightColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val scaledTypography = Typography.scaled(textScale.multiplier)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = scaledTypography,
        content = content
    )
}

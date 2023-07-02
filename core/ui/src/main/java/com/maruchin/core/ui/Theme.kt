package com.maruchin.core.ui

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = Color(0xffc00000),
    onPrimary = Color(0xffffffff),
    primaryContainer = Color(0xffffdad4),
    onPrimaryContainer = Color(0xff410000),
    secondary = Color(0xffc00000),
    onSecondary = Color(0xffffffff),
    secondaryContainer = Color(0xffffdad4),
    onSecondaryContainer = Color(0xff410000),
    tertiary = Color(0xffc00000),
    onTertiary = Color(0xffffffff),
    tertiaryContainer = Color(0xffffdad4),
    onTertiaryContainer = Color(0xff410000),
    error = Color(0xffba1a1a),
    onError = Color(0xffffffff),
    errorContainer = Color(0xffffdad4),
    onErrorContainer = Color(0xff410000),
    background = Color(0xfffffbff),
    onBackground = Color(0xff201a19),
    surface = Color(0xfffffbff),
    onSurface = Color(0xff201a19),
    outline = Color(0xff857370),
    surfaceVariant = Color(0xfff5ddda),
    onSurfaceVariant = Color(0xff534341)
)

private val darkColorScheme = darkColorScheme(
    primary = Color(0xffffb4a8),
    onPrimary = Color(0xff690000),
    primaryContainer = Color(0xff930000),
    onPrimaryContainer = Color(0xffffdad4),
    secondary = Color(0xffffb4a8),
    onSecondary = Color(0xff690000),
    secondaryContainer = Color(0xff930000),
    onSecondaryContainer = Color(0xffffdad4),
    tertiary = Color(0xffffb4a8),
    onTertiary = Color(0xff690000),
    tertiaryContainer = Color(0xff930000),
    onTertiaryContainer = Color(0xffffdad4),
    error = Color(0xffffb4ab),
    onError = Color(0xff690005),
    errorContainer = Color(0xff93000a),
    onErrorContainer = Color(0xffffdad6),
    background = Color(0xff201a19),
    onBackground = Color(0xffede0dd),
    surface = Color(0xff201a19),
    onSurface = Color(0xffede0dd),
    outline = Color(0xffa08c89),
    surfaceVariant = Color(0xff534341),
    onSurfaceVariant = Color(0xffd8c2be)
)

@Composable
fun GymsterTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
            }
        }
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
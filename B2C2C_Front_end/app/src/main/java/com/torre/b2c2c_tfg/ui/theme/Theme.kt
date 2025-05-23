package com.torre.b2c2c_tfg.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = colorPrimaryDark,
    onPrimary = colorOnPrimaryDark,
    secondary = colorSecondaryDark,
    onSecondary = colorOnSecondaryDark,
    background = colorBackgroundDark,
    surface = colorSurfaceDark,
    onSurface = colorOnSurfaceDark,
    error = colorErrorDark,
    onError = colorOnErrorDark,
    outline = colorOutlineDark,
    tertiary = colorTercearyDark,
    surfaceVariant = colorsurfaceVariantDark,
    surfaceContainer = colorSurfaceContainerDark,
)

private val LightColorScheme = lightColorScheme(
    primary = colorPrimaryLight,
    onPrimary = colorOnPrimaryLight,
    secondary = colorSecondaryLight,
    onSecondary = colorOnSecondaryLight,
    background = colorBackgroundLight,
    surface = colorSurfaceLight,
    onSurface = colorOnSurfaceLight,
    error = colorErrorLight,
    onError = colorOnErrorLight,
    outline = colorOutlineLight,
    tertiary = colorTercearyLight,
    surfaceVariant = colorsurfaceVariantLight,
    surfaceContainer = colorSurfaceContainerLight,
)

@Composable
fun B2C2C_TFGTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ , por lo que se desactiva por defecto
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
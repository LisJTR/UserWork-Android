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
    primary = primaryD,
    onPrimary = onPrimaryD,
    secondary = secondaryD,
    onSecondary = onSecondaryD,
    tertiary = tertiaryD,
    onTertiary = onTertiaryD,
    error = errorD,
    onError = onErrorD,
    primaryContainer = primaryContainerD,
    onPrimaryContainer = onPrimaryContainerD,
    secondaryContainer = secondaryContainerD,
    onSecondaryContainer = onSecondaryContainerD,
    tertiaryContainer = tertiaryContainerD,
    onTertiaryContainer = onTertiaryContainerD,
    errorContainer = errorContainerD,
    onErrorContainer = onErrorContainerD,
    surfaceDim = surfaceDimD,
    surface = surfaceD,
    surfaceBright = surfaceBrightD,
    inverseSurface = inverseSurfaceD,
    inverseOnSurface = inverseOnSurfaceD,
    inversePrimary = inversePrimaryD,
    surfaceContainerLowest = surfContrainerLowestD,
    surfaceContainerLow = surfContrainerLowD,
    surfaceContainer = surfContrainerD,
    surfaceContainerHigh = surfContrainerHighD,
    surfaceContainerHighest = surfContrainerHighestD,
    onSurface = onSurfaceD,
    outline = outlineD,
    outlineVariant = outlineVariantD,
    scrim = scrimD,

)

private val LightColorScheme = lightColorScheme(

    primary = primaryL,
    onPrimary = onPrimaryL,
    secondary = secondaryL,
    onSecondary = onSecondaryL,
    tertiary = tertiaryL,
    onTertiary = onTertiaryL,
    error = errorL,
    onError = onErrorL,
    primaryContainer = primaryContainerL,
    onPrimaryContainer = onPrimaryContainerL,
    secondaryContainer = secondaryContainerL,
    onSecondaryContainer = onSecondaryContainerL,
    tertiaryContainer = tertiaryContainerL,
    onTertiaryContainer = onTertiaryContainerL,
    errorContainer = errorContainerL,
    onErrorContainer = onErrorContainerL,
    surfaceDim = surfaceDimL,
    surface = surfaceL,
    surfaceBright = surfaceBrightL,
    inverseSurface = inverseSurfaceL,
    inverseOnSurface = inverseOnSurfaceL,
    inversePrimary = inversePrimaryL,
    surfaceContainerLowest = surfContrainerLowestL,
    surfaceContainerLow = surfContrainerLowL,
    surfaceContainer = surfContrainerL,
    surfaceContainerHigh = surfContrainerHighL,
    surfaceContainerHighest = surfContrainerHighestL,
    onSurface = onSurfaceL,
    outline = outlineL,
    outlineVariant = outlineVariantL,
    scrim = scrimL,



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun B2C2C_TFGTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
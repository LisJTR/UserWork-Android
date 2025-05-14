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
    primary = primaryD,                        // Color principal (botones, acentos, etc.)
    onPrimary = onPrimaryD,                    // Texto/icono sobre primary
    secondary = secondaryD,                    // Color secundario (chips, botones secundarios)
    onSecondary = onSecondaryD,                // Texto/icono sobre secondary
    tertiary = tertiaryD,                      // Color adicional para variar UI
    onTertiary = onTertiaryD,                  // Texto/icono sobre tertiary
    error = errorD,                            // Colores para errores
    onError = onErrorD,                        // Texto sobre color de error

    primaryContainer = primaryContainerD,      // Fondo para elementos destacados (cards, botones)
    onPrimaryContainer = onPrimaryContainerD,  // Texto sobre primaryContainer
    secondaryContainer = secondaryContainerD,  // Fondo para secondary
    onSecondaryContainer = onSecondaryContainerD, // Texto sobre secondaryContainer
    tertiaryContainer = tertiaryContainerD,    // Fondo para tertiary
    onTertiaryContainer = onTertiaryContainerD,// Texto sobre tertiaryContainer
    errorContainer = errorContainerD,          // Fondo para errores (snackbars, alerts)
    onErrorContainer = onErrorContainerD,      // Texto sobre errorContainer

    surfaceDim = surfaceDimD,                  // Fondo más oscuro (background principal en dark)
    surface = surfaceD,                        // Fondo base (pantalla, cards, etc.)
    surfaceBright = surfaceBrightD,            // Fondo claro en dark mode
    inverseSurface = inverseSurfaceD,          // Fondo usado en componentes inversos (ej: Snackbars)
    inverseOnSurface = inverseOnSurfaceD,      // Texto sobre inverseSurface
    inversePrimary = inversePrimaryD,          // Versión invertida de primary (para contraste)

    surfaceContainerLowest = surfContrainerLowestD,  // Fondo muy neutro
    surfaceContainerLow = surfContrainerLowD,
    surfaceContainer = surfContrainerD,
    surfaceContainerHigh = surfContrainerHighD,
    surfaceContainerHighest = surfContrainerHighestD,

    onSurface = onSurfaceD,                    // Texto/iconos sobre surface
    outline = outlineD,                        // Borde de componentes (inputs, cards)
    outlineVariant = outlineVariantD,          // Borde más suave o alternativo
    scrim = scrimD,                             // Efecto de desenfoque/fondo detrás de diálogos
    onSurfaceVariant = onSurfaceVarD,          // Texto sobre outlineVariant
    surfaceVariant = surfaceVariantD           // Fondo más suave o alternativo


)

private val LightColorScheme = lightColorScheme(
    primary = primaryL,
    onPrimary = onPrimaryL,
    secondary = secondaryL,
    onSecondary = onSecondaryL,
    tertiary = tertiaryL,
    onTertiary = onTertiaryL,
    error = errorL, onError = onErrorL,

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

    onSurfaceVariant = onSurfaceVarL,
    onSurface = onSurfaceL,
    outline = outlineL,
    outlineVariant = outlineVariantL,
    scrim = scrimL,
    surfaceVariant = surfaceVariantL,



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
package com.torre.b2c2c_tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.torre.b2c2c_tfg.data.preferences.ThemePreferences
import com.torre.b2c2c_tfg.ui.navigation.AppNavigation
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val sessionViewModel: SessionViewModel = viewModel()

            // 🔁 Recoge el userId (si ya hay sesión)
            val userId = sessionViewModel.userId.collectAsState().value ?: 0L
            val themePrefs = remember { ThemePreferences(context) }

            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            // ⬇️ Cargar tema del usuario una vez al arrancar
            LaunchedEffect(userId) {
                isDarkTheme = themePrefs.getTheme(userId)
            }

            B2C2C_TFGTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    sessionViewModel = sessionViewModel,
                    onToggleTheme = {
                        isDarkTheme = !isDarkTheme
                        // Guardar cambio en preferencias
                        CoroutineScope(Dispatchers.IO).launch {
                            themePrefs.saveTheme(userId, isDarkTheme)
                        }
                    }
                )
            }
        }
    }
}



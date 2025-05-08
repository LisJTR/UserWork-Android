package com.torre.b2c2c_tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.torre.b2c2c_tfg.ui.navigation.AppNavigation
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            // Aplicar el tema personalizado en lugar del tema por defecto
            B2C2C_TFGTheme {
                val sessionViewModel: SessionViewModel = viewModel()
                AppNavigation(sessionViewModel = sessionViewModel)
            }
        }
    }
}



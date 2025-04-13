package com.torre.b2c2c_tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.torre.b2c2c_tfg.ui.navigation.AppNavigation
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            B2C2C_TFGTheme {
                AppNavigation()
            }
        }
    }
}



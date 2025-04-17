package com.torre.b2c2c_tfg.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes.ScreenHome
import com.torre.b2c2c_tfg.ui.screens.LoginEmpresaScreen
import com.torre.b2c2c_tfg.ui.screens.WelcomeScreen
import com.torre.b2c2c_tfg.ui.screens.LoginAlumnoScreen
import com.torre.b2c2c_tfg.ui.screens.ScreenHome

// Definir las rutas de navegación de las pantallas
object ScreenRoutes {
    const val Welcome = "Welcome"
    const val LoginAlumno = "LoginAlumno"
    const val LoginEmpresa = "LoginEmpresa"
    const val ScreenHome = "ScreenHome"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = ScreenRoutes.Welcome) {

        composable(ScreenRoutes.Welcome) {
            WelcomeScreen(navController)
        }

        // Muestra BottomBar
        composable(ScreenRoutes.LoginAlumno) {
            Scaffold(
                bottomBar = { BottomBar(navController, isUserEmpresa = false) }
            ) {
                LoginAlumnoScreen(navController)
            }
        }

        composable(ScreenRoutes.LoginEmpresa) {

            Scaffold(
                bottomBar = { BottomBar(navController, isUserEmpresa = true) }
            ) {
                LoginEmpresaScreen(navController)
            }
        }

        composable(ScreenRoutes.ScreenHome) {
            ScreenHome(navController)
        }

    }
}



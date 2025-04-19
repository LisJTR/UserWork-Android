package com.torre.b2c2c_tfg.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.screens.RegisterProfileEmpresaScreen
import com.torre.b2c2c_tfg.ui.screens.WelcomeScreen
import com.torre.b2c2c_tfg.ui.screens.RegisterProfileAlumnoScreen
import com.torre.b2c2c_tfg.ui.screens.HomeScreen

// Definir las rutas de navegación de las pantallas
object ScreenRoutes {
    const val WelcomeScreen = "Welcome"
    const val AlumnoWithParam = "Register/ProfileAlumno?fromRegistro={fromRegistro}"
    const val EmpresaWithParam = "Register/ProfileEmpresa?fromRegistro={fromRegistro}"
    const val HomeScreen = "HomeScreen"
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = ScreenRoutes.WelcomeScreen) {

        composable(ScreenRoutes.WelcomeScreen) {
            WelcomeScreen(navController)
        }

        composable(

                route = ScreenRoutes.AlumnoWithParam,
                arguments = listOf(navArgument("fromRegistro") {
                    defaultValue = "false"
                })

                // backStackEntry representa la entrada en la pila de navegación
                // y se utiliza para acceder a los argumentos pasados a la pantalla
            ) { backStackEntry ->

            val fromRegistro = backStackEntry.arguments?.getString("fromRegistro")?.toBoolean() ?: false

            if (fromRegistro) {

                RegisterProfileAlumnoScreen(navController)
            } else {

                Scaffold(bottomBar = {
                    BottomBar(navController, isUserEmpresa = false)
                }
                ){ paddingValues ->
                    RegisterProfileAlumnoScreen(navController = navController, contentPadding = paddingValues)
                }
            }
        }

        composable(

                route = ScreenRoutes.EmpresaWithParam,
                arguments = listOf(navArgument("fromRegistro") {
                    defaultValue = "false"
                })

            ) { backStackEntry ->

            val fromRegistro = backStackEntry.arguments?.getString("fromRegistro")?.toBoolean() ?: false

            if (fromRegistro) {
                RegisterProfileEmpresaScreen(navController)

            } else {
                Scaffold(bottomBar = {
                    BottomBar(navController, isUserEmpresa = true)
                }
                ){ paddingValues ->

                    RegisterProfileEmpresaScreen(navController = navController, contentPadding = paddingValues)
                }
            }
        }

        composable(ScreenRoutes.HomeScreen) {
            Scaffold(bottomBar = {
                BottomBar(navController, isUserEmpresa = true) })
            {
                HomeScreen(navController)
            }
        }
    }
}



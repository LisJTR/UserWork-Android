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
import com.torre.b2c2c_tfg.ui.util.UserType

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

                RegisterProfileAlumnoScreen(navController, esEdicion = false)
            } else {

                Scaffold(bottomBar = {
                    BottomBar(navController, userType = UserType.ALUMNO)
                }
                ){ paddingValues ->
                    RegisterProfileAlumnoScreen(
                        navController = navController,
                        contentPadding = paddingValues,
                        esEdicion = true
                    )
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
                RegisterProfileEmpresaScreen(navController, esEdicion = false)

            } else {
                Scaffold(bottomBar = {
                    BottomBar(navController, userType = UserType.EMPRESA)
                })
                { paddingValues ->

                    RegisterProfileEmpresaScreen(
                        navController = navController,
                        contentPadding = paddingValues,
                        esEdicion = true
                    )
                }
            }
        }

        composable(ScreenRoutes.HomeScreen + "?isEmpresa={isEmpresa}") {
                backStackEntry ->

            val isEmpresa = backStackEntry.arguments?.getString("isEmpresa")?.toBoolean() ?: false

            Scaffold(bottomBar = {
                BottomBar(
                    navController,
                    userType = if (isEmpresa) UserType.EMPRESA else UserType.ALUMNO
                )
            }) {
                HomeScreen(
                    navController = navController,
                    isUserEmpresa = isEmpresa
                )
            }
        }

    }
}



package com.torre.b2c2c_tfg.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.screens.RegisterProfileEmpresaScreen
import com.torre.b2c2c_tfg.ui.screens.WelcomeScreen
import com.torre.b2c2c_tfg.ui.screens.RegisterProfileAlumnoScreen
import com.torre.b2c2c_tfg.ui.screens.MisOfertasScreen
import com.torre.b2c2c_tfg.ui.screens.NotificationScreen
import com.torre.b2c2c_tfg.ui.screens.OfertaDetalleScreen
import com.torre.b2c2c_tfg.ui.screens.OfertasScreen
import com.torre.b2c2c_tfg.ui.screens.OfertasScreen
import com.torre.b2c2c_tfg.ui.screens.PerfilDetalleScreen
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import com.torre.b2c2c_tfg.ui.screens.SettingsScreen
import com.torre.b2c2c_tfg.ui.viewmodel.NotificationViewModel


// RUTAS
object ScreenRoutes {
    const val Welcome = "Welcome"
    const val Ofertas = "OfertasScreen"
    const val AlumnoProfile = "Register/ProfileAlumno"
    const val EmpresaProfile = "Register/ProfileEmpresa"
    const val MisOfertas = "MisOfertas"
    const val Settings = "SettingsScreen"
    const val OfertaDetalle = "OfertaDetalleScreen"
    const val PerfilDetalle = "PerfilDetalleScreen"
    const val Notification = "NotificationScreen"

    // rutas con parámetros
    fun ofertas(isEmpresa: Boolean) = "$Ofertas?isEmpresa=$isEmpresa"
    fun alumnoProfile(fromRegistro: Boolean) = "$AlumnoProfile?fromRegistro=$fromRegistro"
    fun empresaProfile(fromRegistro: Boolean) = "$EmpresaProfile?fromRegistro=$fromRegistro"
    fun misOfertasRoute(isEmpresa: Boolean) = "$MisOfertas?isEmpresa=$isEmpresa"
    fun ofertaDetalle(idOferta: Long) = "$OfertaDetalle/$idOferta"
    fun perfilDetalle(idAlumno: Long) = "PerfilDetalleScreen/$idAlumno"
    fun ofertaDetalleDesdeNotificacion(idOferta: Long) = "$OfertaDetalle/$idOferta?modoNotificacion=true"
    fun perfilDetalleDesdeInvitacion(idAlumno: Long, idOferta: Long) = "$PerfilDetalle/$idAlumno/$idOferta"
    fun perfilDetalleDesdeNotificacion(idAlumno: Long, idOferta: Long) = "PerfilDetalleScreen/$idAlumno/$idOferta?desdeNotificacion=true"
    fun ofertaDetalleDesdeMisOfertas(idOferta: Long) = "OfertaDetalleScreen/$idOferta?desdeMisOfertas=true"

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel
) {

    NavHost(navController = navController, startDestination = ScreenRoutes.Welcome) {

        composable(ScreenRoutes.Welcome) {
            WelcomeScreen(navController = navController, sessionViewModel = sessionViewModel)
        }

        // AlumnoProfile con parámetro
        composable(
            route = "${ScreenRoutes.AlumnoProfile}?fromRegistro={fromRegistro}",
            arguments = listOf(navArgument("fromRegistro") { defaultValue = "false" })
        ) { backStackEntry ->
            val fromRegistro = backStackEntry.arguments?.getString("fromRegistro")?.toBoolean() ?: false
            if (fromRegistro) {
                RegisterProfileAlumnoScreen(navController, sessionViewModel = sessionViewModel, esEdicion = false)
            } else {
                Scaffold(bottomBar = {
                    BottomBar(navController, userType = UserType.ALUMNO)
                }) { padding ->
                    RegisterProfileAlumnoScreen(
                        navController = navController,
                        sessionViewModel = sessionViewModel,
                        contentPadding = padding,
                        esEdicion = true
                    )
                }
            }
        }

        // EmpresaProfile con parámetro
        composable(
            route = "${ScreenRoutes.EmpresaProfile}?fromRegistro={fromRegistro}",
            arguments = listOf(navArgument("fromRegistro") { defaultValue = "false" })
        ) { backStackEntry ->
            val fromRegistro = backStackEntry.arguments?.getString("fromRegistro")?.toBoolean() ?: false
            if (fromRegistro) {
                RegisterProfileEmpresaScreen(navController, sessionViewModel = sessionViewModel, esEdicion = false)
            } else {
                Scaffold(bottomBar = {
                    BottomBar(navController, userType = UserType.EMPRESA)
                }) { padding ->
                    RegisterProfileEmpresaScreen(
                        navController = navController,
                        sessionViewModel = sessionViewModel,
                        contentPadding = padding,
                        esEdicion = true
                    )
                }
            }
        }

        // Home con parámetro isEmpresa
        composable(
            route = "${ScreenRoutes.Ofertas}?isEmpresa={isEmpresa}",
            arguments = listOf(navArgument("isEmpresa") { defaultValue = "false" })
        ) { backStackEntry ->
            val isEmpresa = backStackEntry.arguments?.getString("isEmpresa")?.toBoolean() ?: false
            val userType = if (isEmpresa) UserType.EMPRESA else UserType.ALUMNO

            Scaffold(bottomBar = { BottomBar(navController, userType) }) {
                OfertasScreen(navController, isUserEmpresa = isEmpresa, sessionViewModel = sessionViewModel)
            }
        }

        //  MisOfertas con parámetro isEmpresa
        composable(
            route = "${ScreenRoutes.MisOfertas}?isEmpresa={isEmpresa}",
            arguments = listOf(navArgument("isEmpresa") { defaultValue = "false" })
        ) { backStackEntry ->
            val isEmpresa = backStackEntry.arguments?.getString("isEmpresa")?.toBoolean() ?: false
            val userType = if (isEmpresa) UserType.EMPRESA else UserType.ALUMNO

            Scaffold(bottomBar = { BottomBar(navController, userType) }) {
                MisOfertasScreen(navController = navController, isUserEmpresa = isEmpresa, sessionViewModel = sessionViewModel)
            }
        }

        composable(ScreenRoutes.Settings) {
            val userType = when (sessionViewModel.userType.collectAsState().value) {
                "empresa" -> UserType.EMPRESA
                "alumno" -> UserType.ALUMNO
                else -> UserType.ALUMNO
            }

            Scaffold(bottomBar = { BottomBar(navController, userType) }) {
                SettingsScreen( navController = navController)
            }
        }

        composable(
            route = "${ScreenRoutes.OfertaDetalle}/{idOferta}?modoNotificacion={modoNotificacion}",
            arguments = listOf(
                navArgument("idOferta") { type = NavType.LongType },
                navArgument("modoNotificacion") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            val modoNotificacion = backStackEntry.arguments?.getBoolean("modoNotificacion") ?: false

            OfertaDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idOferta = idOferta,
                modoNotificacion = modoNotificacion
            )
        }

        composable(
            route = "PerfilDetalleScreen/{idAlumno}",
            arguments = listOf(navArgument("idAlumno") { type = NavType.LongType })
        ) { backStackEntry ->
            val idAlumno = backStackEntry.arguments?.getLong("idAlumno") ?: 0L
            PerfilDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idAlumno = idAlumno
            )
        }

        composable(ScreenRoutes.Notification) {
            NotificationScreen(
                sessionViewModel = sessionViewModel,
                navController = navController,

            )
        }

        // Navegacción con más de un parámetro
        composable(
            route = "PerfilDetalleScreen/{idAlumno}/{idOferta}",
            arguments = listOf(
                navArgument("idAlumno") { type = NavType.LongType },
                navArgument("idOferta") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val idAlumno = backStackEntry.arguments?.getLong("idAlumno") ?: 0L
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            PerfilDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idAlumno = idAlumno,
                idOfertaPreSeleccionada = idOferta // lo pasas como nuevo parámetro
            )
        }

        composable(
            route = "PerfilDetalleScreen/{idAlumno}/{idOferta}?desdeNotificacion={desdeNotificacion}",
            arguments = listOf(
                navArgument("idAlumno") { type = NavType.LongType },
                navArgument("idOferta") { type = NavType.LongType },
                navArgument("desdeNotificacion") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val idAlumno = backStackEntry.arguments?.getLong("idAlumno") ?: 0L
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            val desdeNotificacion = backStackEntry.arguments?.getBoolean("desdeNotificacion") ?: false

            PerfilDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idAlumno = idAlumno,
                idOfertaPreSeleccionada = idOferta,
                desdeNotificacion = desdeNotificacion
            )
        }

        composable(
            route = "${ScreenRoutes.OfertaDetalle}/{idOferta}?desdeMisOfertas={desdeMisOfertas}",
            arguments = listOf(
                navArgument("idOferta") { type = NavType.LongType },
                navArgument("desdeMisOfertas") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            val desdeMisOfertas = backStackEntry.arguments?.getBoolean("desdeMisOfertas") ?: false

            OfertaDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idOferta = idOferta,
                desdeMisOfertas = desdeMisOfertas
            )
        }



    }
}
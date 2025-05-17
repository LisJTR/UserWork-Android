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

// RUTAS PRINCIPALES DE LA APP
object ScreenRoutes {
    // Rutas "simples" (sin parámetros)
    const val Welcome = "Welcome" // Pantalla de bienvenida
    const val Ofertas = "OfertasScreen" // Pantalla principal de ofertas
    const val AlumnoProfile = "Register/ProfileAlumno" // Registro/Edición de perfil alumno
    const val EmpresaProfile = "Register/ProfileEmpresa" // Registro/Edición de perfil empresa
    const val MisOfertas = "MisOfertas" // Pantalla de mis ofertas (alumno/empresa)
    const val Settings = "SettingsScreen" // Pantalla de ajustes
    const val OfertaDetalle = "OfertaDetalleScreen" // Pantalla de detalle de oferta
    const val PerfilDetalle = "PerfilDetalleScreen" // Pantalla de detalle de alumno
    const val Notification = "NotificationScreen"  // Pantalla de notificaciones

    // FUNCIONES para construir rutas con parámetros

    // Home de ofertas, distingue si es alumno o empresa
    fun ofertas(isEmpresa: Boolean) = "$Ofertas?isEmpresa=$isEmpresa"
    // Registro o edición del perfil alumno
    fun alumnoProfile(fromRegistro: Boolean) = "$AlumnoProfile?fromRegistro=$fromRegistro"
    // Registro o edición del perfil empresa
    fun empresaProfile(fromRegistro: Boolean) = "$EmpresaProfile?fromRegistro=$fromRegistro"
    // Ruta a la pantalla MisOfertas, distingue por tipo de usuario
    fun misOfertasRoute(isEmpresa: Boolean) = "$MisOfertas?isEmpresa=$isEmpresa"
    // Detalle de oferta normal (sin estado adicional)
    fun ofertaDetalle(idOferta: Long) = "$OfertaDetalle/$idOferta"
    // Detalle de perfil de alumno (sin oferta asociada)
    fun perfilDetalle(idAlumno: Long) = "$PerfilDetalle/$idAlumno"
    // Detalle de perfil desde notificación, con todos los datos necesarios
    fun perfilDetalleDesdeNotificacionEmpresa(idAlumno: Long, idOferta: Long, notificacionId: Long, estadoRespuesta: String? = null) =
        "$PerfilDetalle/$idAlumno/$idOferta/$notificacionId?desdeNotificacion=true&estadoRespuesta=${estadoRespuesta ?: ""}"
    // Detalle de oferta desde notificación, con ID de notificación
    fun ofertaDetalleDesdeNotificacionAlumno(idOferta: Long, idNotificacion: Long) = "$OfertaDetalle/$idOferta/$idNotificacion?modoNotificacion=true"
    // Detalle perfil desde mis ofertas EMPRESA (vista igual que notificación)
    fun perfilDetalleDesdeMisOfertasEmpresa(idAlumno: Long, idOferta: Long, idNotificacion: Long, estadoRespuesta: String?) =
        "$PerfilDetalle/$idAlumno/$idOferta/$idNotificacion?desdeNotificacion=true&estadoRespuesta=${estadoRespuesta ?: ""}"
    // Detalle de oferta desde mis ofertas ALUMNO (vista igual que notificación)
    fun ofertaDetalleDesdeMisOfertasAlumno(idOferta: Long, idNotificacion: Long) =
        "$OfertaDetalle/$idOferta/$idNotificacion?modoNotificacion=true"

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel
) {

    NavHost(navController = navController, startDestination = ScreenRoutes.Welcome) {

        // Pantalla de bienvenida
        composable(ScreenRoutes.Welcome) {
            WelcomeScreen(navController = navController, sessionViewModel = sessionViewModel)
        }

        // Pantalla principal ofertas (distingue por isEmpresa)
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

        // Mis ofertas (distingue por isEmpresa)
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

        // Registro/edición perfil alumno
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

        // Registro/edición perfil empresa
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


        // Ajustes
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

        // Pantalla de notificaciones
        composable(ScreenRoutes.Notification) {
            NotificationScreen(
                sessionViewModel = sessionViewModel,
                navController = navController,

                )
        }


        // Detalle de oferta desde notificación
        composable(
            route = "${ScreenRoutes.OfertaDetalle}/{idOferta}/{idNotificacion}?modoNotificacion={modoNotificacion}",
            arguments = listOf(
                navArgument("idOferta") { type = NavType.LongType },
                navArgument("idNotificacion") { type = NavType.LongType },
                navArgument("modoNotificacion") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            val idNotificacion = backStackEntry.arguments?.getLong("idNotificacion") ?: 0L
            val modoNotificacion = backStackEntry.arguments?.getBoolean("modoNotificacion") ?: false

            OfertaDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idOferta = idOferta,
                modoNotificacion = modoNotificacion,
                idNotificacion = idNotificacion
            )
        }

        // Detalle de perfil desde notificación con estado de respuesta
        composable(
            route = "PerfilDetalleScreen/{idAlumno}/{idOferta}/{notificacionId}?desdeNotificacion={desdeNotificacion}&estadoRespuesta={estadoRespuesta}",
            arguments = listOf(
                navArgument("idAlumno") { type = NavType.LongType },
                navArgument("idOferta") { type = NavType.LongType },
                navArgument("notificacionId") { type = NavType.LongType },
                navArgument("desdeNotificacion") {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument("estadoRespuesta") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val idAlumno = backStackEntry.arguments?.getLong("idAlumno") ?: 0L
            val idOferta = backStackEntry.arguments?.getLong("idOferta") ?: 0L
            val notificacionId = backStackEntry.arguments?.getLong("notificacionId") ?: 0L
            val desdeNotificacion = backStackEntry.arguments?.getBoolean("desdeNotificacion") ?: false


            PerfilDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idAlumno = idAlumno,
                idOfertaPreSeleccionada = idOferta,
                desdeNotificacion = desdeNotificacion,
                idNotificacion = notificacionId
            )
        }


        // Detalle de perfil de alumno (solo ID alumno)
        composable(
            route = "${ScreenRoutes.PerfilDetalle}/{idAlumno}",
            arguments = listOf(navArgument("idAlumno") { type = NavType.LongType })
        ) { backStackEntry ->
            val idAlumno = backStackEntry.arguments?.getLong("idAlumno") ?: 0L
            PerfilDetalleScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                idAlumno = idAlumno
            )
        }



    }
}
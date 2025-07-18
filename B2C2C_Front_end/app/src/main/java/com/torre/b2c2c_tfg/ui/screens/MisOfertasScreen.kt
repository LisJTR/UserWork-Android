package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.AplicacionOfertaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.InvitacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAplicacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.EmpresaCard
import com.torre.b2c2c_tfg.ui.components.HeaderContentofScreens
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.MisOfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.NotificationViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import java.time.Instant
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.swiperefresh.SwipeRefresh

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MisOfertasScreen(
    navController: NavController,
    isUserEmpresa: Boolean,
    sessionViewModel: SessionViewModel,
) {
    val context = LocalContext.current
    val entradaDesdeMisOfertas = true

    val viewModel = remember {
        MisOfertasScreenViewModel(
            getAplicacionesUseCase = GetAplicacionesPorAlumnoUseCase(AplicacionOfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getTodasLasOfertasUseCase = GetTodasLasOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getInvitacionesPorEmpresaUseCase = GetInvitacionPorEmpresaUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val headerViewModel = remember {
        OfertasScreenViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            getEmpresaUseCase = GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getSectoresUnicosUseCase = GetSectoresUnicosUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getTitulacionesUnicasUseCase = GetTitulacionesUnicasUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            alumnoRepository = AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)),
            empresaRepository = EmpresaRepositoryImpl(RetrofitInstance.getInstance(context)),
            getTodasLasOfertasUseCase = GetTodasLasOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val notificationViewModel = remember {
        NotificationViewModel(
            getNotificacionesPorAlumnoUseCase = GetNotificacionesPorAlumnoUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getNotificacionesPorEmpresaUseCase = GetNotificacionesPorEmpresaUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))
            )
        )
    }

    val notificaciones by notificationViewModel.notificaciones.collectAsState()

    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value
    val ofertas by viewModel.ofertasAplicadas.collectAsState()
    val empresas by headerViewModel.empresas.collectAsState()
    val invitaciones by viewModel.invitacionesRecibidas.collectAsState()
    val ofertasFiltradas by headerViewModel.ofertasFiltradas.collectAsState(initial = emptyList())
    val alumnosFiltrados by headerViewModel.alumnosFiltrados.collectAsState(initial = emptyList())

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    fun recargarDatos() {
        if (userType == "alumno") {
            headerViewModel.cargarAlumno(userId)
            headerViewModel.cargarEmpresas()
            viewModel.cargarOfertasAplicadas(userId)
            notificationViewModel.cargarNotificaciones(userId, "alumno")
        } else {
            notificationViewModel.cargarNotificaciones(userId, "empresa")
            headerViewModel.cargarEmpresa(userId)
            viewModel.cargarOfertasAplicadas(userId)
            viewModel.cargarInvitaciones(userId)
            headerViewModel.cargarAlumnos()
            headerViewModel.cargarTodasLasOfertas()
        }
    }
    LaunchedEffect(Unit) { recargarDatos() }


    LaunchedEffect(Unit) {
        if (userType == "alumno") {
            viewModel.iniciarAutoRefresco(alumnoId = userId, empresaId = null)
        } else if (userType == "empresa") {
            viewModel.iniciarAutoRefresco(alumnoId = null, empresaId = userId)
        }
    }


    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true
            recargarDatos()
            isRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .systemBarsPadding()
        ) {
            HeaderContentofScreens(
                sessionViewModel = sessionViewModel,
                notificationViewModel = notificationViewModel,
                viewModel = headerViewModel,
                onFiltroSeleccionado = {},
                navController = navController
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {

                if (userType == "alumno") {
                    items(ofertas) { oferta ->
                        val empresa = empresas.find { it.id?.toLong() == oferta.empresaId.toLong() }
                        val notificacionRelacionada = notificaciones
                            .filter {
                                it.ofertaId == oferta.id?.toLong() &&
                                        it.alumnoId == userId &&
                                        it.tipo in listOf("aplicacion", "respuesta")
                            }
                            .maxByOrNull { Instant.parse(it.fecha).toEpochMilli() }

                        empresa?.let {
                            EmpresaCard(
                                nombre = it.nombre,
                                sector = it.sector,
                                descripcion = "${oferta.titulo}\nRespuesta: ${notificacionRelacionada?.estadoRespuesta?.uppercase() ?: "PENDIENTE"}",
                                imagenUri = RetrofitInstance.buildUri(it.imagen),
                                onClick = {
                                    val idNotificacion = notificacionRelacionada?.id?.toLong() ?: 0L
                                    navController.navigate(
                                        ScreenRoutes.ofertaDetalleDesdeMisOfertasAlumno(
                                            oferta.id?.toLong() ?: 0L,
                                            idNotificacion
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                if (userType == "empresa") {
                    items(invitaciones) { invitacion ->
                        val oferta = ofertasFiltradas.find { it.id?.toLong() == invitacion.ofertaId }
                        val alumno = alumnosFiltrados.find { it.id?.toLong() == invitacion.alumnoId }
                        val notificacionRelacionada = notificaciones
                            .filter {
                                it.ofertaId == invitacion.ofertaId &&
                                        it.alumnoId == invitacion.alumnoId &&
                                        it.empresaId == userId &&
                                        it.tipo in listOf("invitacion", "respuesta")
                            }
                            .maxByOrNull { Instant.parse(it.fecha).toEpochMilli() }

                        if (oferta != null && alumno != null) {
                            EmpresaCard(
                                nombre = "${alumno.nombre} ${alumno.apellido}",
                                sector = alumno.titulacion,
                                descripcion = "${oferta.titulo}\nRespuesta: ${notificacionRelacionada?.estadoRespuesta?.uppercase() ?: "PENDIENTE"}",
                                imagenUri = RetrofitInstance.buildUri(alumno.imagen),
                                onClick = {
                                    val idNotificacion = notificacionRelacionada?.id?.toLong() ?: 0L
                                    val estadoRespuesta = notificacionRelacionada?.estadoRespuesta ?: ""
                                    navController.navigate(
                                        ScreenRoutes.perfilDetalleDesdeMisOfertasEmpresa(
                                            alumno.id?.toLong() ?: 0L,
                                            oferta.id?.toLong() ?: 0L,
                                            idNotificacion,
                                            estadoRespuesta
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}




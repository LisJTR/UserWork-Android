package com.torre.b2c2c_tfg.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
import com.torre.b2c2c_tfg.ui.viewmodel.NotificationViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import androidx.compose.runtime.getValue
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.ui.components.FiltroDropdown
import com.torre.b2c2c_tfg.ui.components.IconFilter
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.util.FiltroNotificacion
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.torre.b2c2c_tfg.ui.components.IconArrowBack


@Composable
fun NotificationScreen(
    sessionViewModel: SessionViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value ?: ""

    val notificacionViewModel = remember {
        NotificationViewModel(
            getNotificacionesPorAlumnoUseCase = GetNotificacionesPorAlumnoUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getNotificacionesPorEmpresaUseCase = GetNotificacionesPorEmpresaUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val filtro by notificacionViewModel.filtroActual.collectAsState()
    var showFiltroMenu by remember { mutableStateOf(false) }
    val notificaciones by notificacionViewModel.notificacionesFiltradas.collectAsState(initial = emptyList())


    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    LaunchedEffect(currentBackStackEntry ) {
        sessionViewModel.userType.value?.let { tipoUsuario ->
            notificacionViewModel.cargarNotificaciones(userId, tipoUsuario)
        }
    }

    // 👇 AQUI ESTA LA CLAVE:
    LaunchedEffect(Unit) {
        notificacionViewModel.iniciarAutoRefresco(userId, userType)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconArrowBack( onClick = { navController.popBackStack() })
            Text("Notificaciones", style = MaterialTheme.typography.titleLarge)
            Box {
                IconFilter(onClick = { showFiltroMenu = true })

                FiltroDropdown(
                    expanded = showFiltroMenu,
                    onDismissRequest = { showFiltroMenu = false },
                    opciones = listOf("Todas", "Respondidas", "Pendientes"),
                    onSeleccion = {
                        notificacionViewModel.filtroActual.value = when (it) {
                            "Respondidas" -> FiltroNotificacion.RESPONDIDAS
                            "Pendientes" -> FiltroNotificacion.PENDIENTES
                            else -> FiltroNotificacion.TODAS
                        }
                        showFiltroMenu = false
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // IMPORTANTE: ocupa el resto del espacio y permite scroll
        ) {
            items(notificaciones) { notificacion ->

                val backgroundColor = when (notificacion.tipo) {
                    "respuesta" -> when (notificacion.estadoRespuesta) {
                        "seleccionado", "inter_mutuo" -> MaterialTheme.colorScheme.primary
                        "descartado", "no_interesado" -> MaterialTheme.colorScheme.secondary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            notificacionViewModel.marcarComoLeida(notificacion)
                            when (notificacion.destinatarioTipo) {
                                "alumno" -> {
                                    val idOferta = notificacion.ofertaId ?: return@clickable
                                    navController.navigate(
                                        ScreenRoutes.ofertaDetalleDesdeNotificacionAlumno(
                                            idOferta,
                                            notificacion.id?.toLong() ?: 0L
                                        )
                                    )
                                }
                                "empresa" -> {
                                    val idAlumno = notificacion.alumnoId ?: return@clickable
                                    val idOferta = notificacion.ofertaId ?: return@clickable
                                    navController.navigate(
                                        ScreenRoutes.perfilDetalleDesdeNotificacionEmpresa(
                                            idAlumno,
                                            idOferta,
                                            notificacion.id?.toLong() ?: 0L,
                                            notificacion.estadoRespuesta
                                        )
                                    )
                                }
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = notificacion.mensaje, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Estado: ${notificacion.estadoRespuesta ?: "pendiente"}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Visto: ${if (notificacion.leido) "Sí" else "No"}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

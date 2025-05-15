package com.torre.b2c2c_tfg.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes


@Composable
fun NotificationScreen(
    sessionViewModel: SessionViewModel,
    navController: NavController,

) {
    val context = LocalContext.current
    val userId = sessionViewModel.userId.collectAsState().value ?: 0L

    val notificacionViewModel = remember {
        NotificationViewModel(
            getNotificacionesPorAlumnoUseCase = GetNotificacionesPorAlumnoUseCase(
                NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))
            ),
            getNotificacionesPorEmpresaUseCase = GetNotificacionesPorEmpresaUseCase(
                NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))
            ),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(
                NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))
            )
        )
    }

    val notificaciones by notificacionViewModel.notificaciones.collectAsState()

    LaunchedEffect(userId) {
        sessionViewModel.userType.value?.let { tipoUsuario ->
            notificacionViewModel.cargarNotificaciones(userId, tipoUsuario)
        }
    }



    LazyColumn {
        item {
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(notificaciones) { notificacion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        val idOferta = notificacion.ofertaId ?: return@clickable
                        // Marcar como leída
                        notificacionViewModel.marcarComoLeida(notificacion)
                        // Navegar a la oferta detalle
                        navController.navigate(ScreenRoutes.ofertaDetalleDesdeNotificacion(idOferta))
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = notificacion.mensaje, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Estado: ${notificacion.estadoRespuesta}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Visto: ${if (notificacion.leido) "Sí" else "No"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
package com.torre.b2c2c_tfg.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AplicacionOfertaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.ComprobarAplicacionExistenteUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearAplicacionOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionPorIdUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetOfertaByIdUseCase
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.viewmodel.OfertaDetalleScreenViewModel
import com.torre.b2c2c_tfg.ui.components.PerfilDetalleHeader
import com.torre.b2c2c_tfg.ui.components.SectionDescription
import com.torre.b2c2c_tfg.ui.components.TextInputLabel
import com.torre.b2c2c_tfg.ui.components.TextTitle

@Composable
fun OfertaDetalleScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel,
    idOferta: Long,
    modoNotificacion: Boolean = false, // Parámetro para determinar el modo de la pantalla
    idNotificacion: Long? = null,
    entradaDesdeMisOfertas: Boolean = false

) {
    val context = LocalContext.current
    val viewModel = remember {
        OfertaDetalleScreenViewModel(
            getOfertaByIdUseCase = GetOfertaByIdUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getEmpresaUseCase = GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearAplicacionOfertaUseCase = CrearAplicacionOfertaUseCase(AplicacionOfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearNotificacionUseCase = CrearNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            comprobarAplicacionExistenteUseCase = ComprobarAplicacionExistenteUseCase(AplicacionOfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getNotificacionPorIdUseCase = GetNotificacionPorIdUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
        )
    }

    LaunchedEffect(idOferta) {
        viewModel.cargarOfertaConEmpresa(idOferta)
    }

    val alumnoId = sessionViewModel.userId.collectAsState().value ?: 0L

    LaunchedEffect(alumnoId, idOferta) {
        viewModel.comprobarSiYaAplicada(alumnoId, idOferta)
    }

    val empresa by viewModel.empresa.collectAsState()
    val oferta by viewModel.oferta.collectAsState()
    val aplicacionExitosa by viewModel.aplicacionExitosa.collectAsState()
    val yaAplicada by viewModel.yaAplicada.collectAsState()

    LaunchedEffect(aplicacionExitosa) {
        when (aplicacionExitosa) {
            true -> {
                Toast.makeText(context, "Aplicación enviada", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            false -> Toast.makeText(context, "Error al aplicar", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }
    val estadoRespuestaBackend by viewModel.estadoRespuesta.collectAsState()
    val tipoNotificacionBackend by viewModel.tipoNotificacion.collectAsState()

    LaunchedEffect(idNotificacion) {
        idNotificacion?.let { viewModel.cargarEstadoRespuesta(it) }
        println("🪵 OfertaDetalleScreen - idNotificacion recibido: $idNotificacion")

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Habilita el desplazamiento vertical
    ) {
        empresa?.let {
            PerfilDetalleHeader(
                imagenUri = RetrofitInstance.buildUri(empresa?.imagen),
                nombre = it.nombre ?: "Empresa"
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextInputLabel(text = "Sector")
            SectionDescription(text = it.sector)

            Spacer(modifier = Modifier.height(8.dp))

            TextInputLabel(text = "Ciudad")
            SectionDescription(text = it.ciudad)

            Spacer(modifier = Modifier.height(8.dp))

            TextInputLabel(text = "Correo electrónico")
            SectionDescription(text = it.correoElectronico ?: "-")

            Spacer(modifier = Modifier.height(8.dp))

            TextInputLabel(text = "Descripción")
            SectionDescription(text = it.descripcion)
        }

        Spacer(modifier = Modifier.height(25.dp))

        oferta?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceDim)
                    .padding(16.dp)
            ) {
                TextTitle(
                    text = "Detalles de la oferta",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextInputLabel(text = "Fecha de publicación")
                SectionDescription(text = it.fechaPublicacion ?: "-")

                Spacer(modifier = Modifier.height(8.dp))

                TextInputLabel(text = "Título")
                SectionDescription(text = it.titulo ?: "-")

                Spacer(modifier = Modifier.height(8.dp))

                TextInputLabel(text = "Qué se ofrece")
                SectionDescription(text = it.queSeOfrece ?: "-")

                Spacer(modifier = Modifier.height(8.dp))

                TextInputLabel(text = "Aptitudes")
                SectionDescription(text = it.aptitudes ?: "-")

                Spacer(modifier = Modifier.height(8.dp))

                TextInputLabel(text = "Descripción de la oferta")
                SectionDescription(text = it.descripcion ?: "-")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (modoNotificacion) {
            if ((estadoRespuestaBackend == null || estadoRespuestaBackend == "pendiente") && !entradaDesdeMisOfertas) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonGeneric(
                        text = "INTERÉS MUTUO",
                        onClick = {
                            idNotificacion?.let {
                                viewModel.responderNotificacion(it, "inter_mutuo")
                                Toast.makeText(context, "Interés mutuo registrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    ButtonGeneric(
                        text = "NO INTERESADO",
                        onClick = {
                            idNotificacion?.let {
                                viewModel.responderNotificacion(it, "no_interesado")
                                Toast.makeText(context, "No interesado", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            } else {
                if (tipoNotificacionBackend != "respuesta") {
                    estadoRespuestaBackend?.let { respuesta ->
                        Text(
                            text = "Respondido: ${estadoRespuestaBackend!!.uppercase()}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                } else {
                    estadoRespuestaBackend?.let { respuesta ->
                        Text(
                            text = estadoRespuestaBackend!!.uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }
        else {
            if (yaAplicada) {
                Text(
                    text = "Ya has aplicado a esta oferta.",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            ButtonGeneric(
                text = "APLICAR",
                onClick = {
                    val alumnoId = sessionViewModel.userId.value ?: return@ButtonGeneric
                    val ofertaId = oferta?.id?.toLong() ?: return@ButtonGeneric
                    println("Aplicando a la oferta con ID: ${oferta?.id}")
                    Toast.makeText(context, "Pulsado botón APLICAR", Toast.LENGTH_SHORT).show()
                    viewModel.aplicarAOferta(alumnoId, ofertaId)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                enabled = !yaAplicada
            )

        }
    }
}

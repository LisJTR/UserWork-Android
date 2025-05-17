package com.torre.b2c2c_tfg.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.IconArrowBack
import com.torre.b2c2c_tfg.ui.components.PerfilDetalleHeader
import com.torre.b2c2c_tfg.ui.components.TextInputLabel
import com.torre.b2c2c_tfg.ui.components.VisualHabilidadChip
import com.torre.b2c2c_tfg.ui.viewmodel.PerfilDetalleViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.graphics.Color
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.repository.InvitacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearInvitacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEstadoRespuestaPorIdUseCase
import com.torre.b2c2c_tfg.ui.components.OfertaSeleccionDialog
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase
import com.torre.b2c2c_tfg.ui.components.IconArrowDown


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PerfilDetalleScreen(
    navController: NavController,
    idAlumno: Long,
    sessionViewModel: SessionViewModel,
    idOfertaPreSeleccionada: Long? = null,
    desdeNotificacion: Boolean = false,
    estadoRespuesta: String? = null,
    idNotificacion: Long? = null

) {
    val context = LocalContext.current
    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L
    var listaOfertas by remember { mutableStateOf<List<Oferta>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    var ofertaSeleccionadaTitulo by remember { mutableStateOf("OFERTAS ACTIVAS") }
    var ofertaSeleccionadaId by remember { mutableStateOf<Long?>(null) }

    val viewModel = remember {
        PerfilDetalleViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearInvitacionUseCase = CrearInvitacionUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearNotificacionUseCase = CrearNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getInvitacionesPorEmpresaUseCase = GetInvitacionPorEmpresaUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val alumno by viewModel.alumno.collectAsState()
    val ofertasYaUsadas = viewModel.idsOfertasYaUsadas.collectAsState().value

    LaunchedEffect(idAlumno) {
        viewModel.cargarAlumno(idAlumno)
        viewModel.cargarInvitacionesEnviadas(empresaId, idAlumno)
    }

    LaunchedEffect(empresaId) {
        val repo = OfertaRepositoryImpl(RetrofitInstance.getInstance(context))
        val useCase = GetOfertasUseCase(repo)
        listaOfertas = useCase(empresaId).filter { it.publicada }
        if (idOfertaPreSeleccionada != null) {
            val seleccionada = listaOfertas.find { it.id?.toLong() == idOfertaPreSeleccionada }
            ofertaSeleccionadaTitulo = seleccionada?.titulo ?: "OFERTA ASOCIADA"
            ofertaSeleccionadaId = idOfertaPreSeleccionada
            showDialog = false
        }
    }

    alumno?.let {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconArrowBack(onClick = { navController.popBackStack() })
            }

            PerfilDetalleHeader(imagenUrl = it.imagen, nombre = "${it.nombre} ${it.apellido}")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (desdeNotificacion && ofertaSeleccionadaTitulo.isNotBlank()) {
                    Text(
                        text = "Aplicando: $ofertaSeleccionadaTitulo",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                TextInputLabel("Teléfono: ${it.telefono}")
                TextInputLabel("Email: ${it.correoElectronico ?: "No disponible"}")
                TextInputLabel("Ciudad: ${it.ciudad}")
                TextInputLabel("Dirección: ${it.direccion}")
                TextInputLabel("Centro: ${it.centro}")
                TextInputLabel("Titulación: ${it.titulacion}")
                TextInputLabel("Descripción: ${it.descripcion}")
                TextInputLabel("Habilidades:")

                FlowRow {
                    it.habilidades.split(",").map { h -> h.trim() }.filter { it.isNotBlank() }.forEach {
                        VisualHabilidadChip(it)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ButtonGeneric(text = "Ver CV", onClick = {})
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (desdeNotificacion) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonGeneric(text = "SELECCIONADO",
                            onClick = {
                                idNotificacion?.let {
                                    viewModel.responderNotificacion(it, "seleccionado")
                                    Toast.makeText(context, "Alumno seleccionado", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                        ButtonGeneric(text = "DESCARTADO",
                            onClick = {
                                idNotificacion?.let {
                                    viewModel.responderNotificacion(it, "descartado")
                                    Toast.makeText(context, "Alumno descartado", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                } else {
                    val yaInvitada = ofertaSeleccionadaId in ofertasYaUsadas
                    val todasUsadas = listaOfertas.all { it.id?.toLong() in ofertasYaUsadas }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ButtonGeneric(
                            text = ofertaSeleccionadaTitulo,
                            onClick = { if (idOfertaPreSeleccionada == null) showDialog = true },
                            enabled = idOfertaPreSeleccionada == null,
                            modifier = Modifier.wrapContentWidth()
                        )

                        ButtonGeneric(
                            text = "INTERESADO",
                            onClick = {
                                ofertaSeleccionadaId?.let {
                                    viewModel.enviarInvitacion(empresaId, it, idAlumno)
                                    Toast.makeText(context, "Interesado a la oferta", Toast.LENGTH_SHORT).show()
                                }
                            },
                            enabled = ofertaSeleccionadaId != null && !yaInvitada,
                            modifier = Modifier.wrapContentWidth()
                        )

                        if (idOfertaPreSeleccionada != null && !desdeNotificacion) {
                            IconArrowDown()

                            ButtonGeneric(
                                text = "INTERÉS MUTUO",
                                onClick = {},
                                enabled = false,
                                containerColor = if (estadoRespuesta == "inter_mutuo")
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.wrapContentWidth()
                            )

                            IconArrowDown()

                            ButtonGeneric(
                                text = "NO INTERESADO",
                                onClick = {},
                                enabled = false,
                                containerColor = if (estadoRespuesta == "no_interesado")
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.wrapContentWidth()
                            )
                        }

                        if (todasUsadas) {
                            Text(
                                text = "Ya se ha mostrado interés en el usuario en todas las ofertas disponibles.",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                OfertaSeleccionDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    ofertas = listaOfertas.map { it.titulo },
                    deshabilitadas = listaOfertas.filter { it.id?.toLong() in ofertasYaUsadas }.map { it.titulo },
                    onSeleccion = { seleccionTitulo ->
                        val ofertaSeleccionada = listaOfertas.find { it.titulo == seleccionTitulo }
                        ofertaSeleccionadaTitulo = seleccionTitulo
                        ofertaSeleccionadaId = ofertaSeleccionada?.id?.toLong()
                    }
                )
            }
        }
    }
}
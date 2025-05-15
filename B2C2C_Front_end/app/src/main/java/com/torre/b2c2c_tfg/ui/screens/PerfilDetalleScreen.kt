package com.torre.b2c2c_tfg.ui.screens

import android.graphics.drawable.Icon
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.graphics.Color
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.repository.InvitacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.CrearInvitacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearNotificacionUseCase
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.FiltroDropdown
import com.torre.b2c2c_tfg.ui.components.OfertaSeleccionDialog
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase




@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PerfilDetalleScreen(navController: NavController, idAlumno: Long, sessionViewModel: SessionViewModel) {
    val context = LocalContext.current
    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L
    var listaOfertas by remember { mutableStateOf<List<Oferta>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    var ofertaSeleccionadaTitulo by remember { mutableStateOf("OFERTAS ACTIVAS") }
    var ofertaSeleccionadaId by remember { mutableStateOf<Long?>(null) }


    LaunchedEffect(empresaId) {
        try {
            println(">> ID EMPRESA USUARIO ACTUAL: $empresaId")
            val repo = OfertaRepositoryImpl(RetrofitInstance.getInstance(context))
            val useCase = GetOfertasUseCase(repo)
            listaOfertas = useCase(empresaId).filter { it.publicada }
            println(">> OFERTAS CARGADAS: ${listaOfertas.map { it.titulo }}")
        } catch (e: Exception) {
            println("Error cargando ofertas: ${e.message}")
        }
    }
    val viewModel = remember {
        PerfilDetalleViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearInvitacionUseCase = CrearInvitacionUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            crearNotificacionUseCase = CrearNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getInvitacionesPorEmpresaUseCase = GetInvitacionPorEmpresaUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context)))

        )
    }

    val alumno by viewModel.alumno.collectAsState()

    LaunchedEffect(idAlumno) {
        viewModel.cargarAlumno(idAlumno)
        viewModel.cargarInvitacionesEnviadas(empresaId, idAlumno)
    }

    alumno?.let {
        Column {
            // Flecha volver arriba a la izquierda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconArrowBack(onClick = { navController.popBackStack() })
            }

            // Cabecera con foto y nombre
            PerfilDetalleHeader(
                imagenUrl = it.imagen,
                nombre = "${it.nombre} ${it.apellido}"
            )

            // Info del alumno
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextInputLabel("Teléfono: ${it.telefono}")
                TextInputLabel("Email: ${it.correoElectronico ?: "No disponible"}")
                TextInputLabel("Ciudad: ${it.ciudad}")
                TextInputLabel("Dirección: ${it.direccion}")
                TextInputLabel("Centro: ${it.centro}")
                TextInputLabel("Titulación: ${it.titulacion}")
                TextInputLabel("Descripción: ${it.descripcion}")
                TextInputLabel("Habilidades:")

                FlowRow {
                    it.habilidades
                        .split(",") // o ajusta si usas espacios o puntos y coma
                        .map { habilidad -> habilidad.trim() }
                        .filter { it.isNotBlank() }
                        .forEach { habilidad ->
                            VisualHabilidadChip(habilidad)
                        }
                }

                // Botones
                Spacer(modifier = Modifier.height(16.dp))
                // Botón "Ver CV" centrado arriba
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ButtonGeneric(text = "Ver CV", onClick = {
                        // TODO: Acción para ver el CV
                    })
                }

                Spacer(modifier = Modifier.height(12.dp))

            // Botones "OFERTAS" e "INTERESADO" centrados debajo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonGeneric(
                        text = ofertaSeleccionadaTitulo,
                        onClick = { showDialog = true }
                    )
                    val ofertasYaUsadas = viewModel.idsOfertasYaUsadas.collectAsState().value

                    val todasUsadas = listaOfertas.all { it.id?.toLong() in ofertasYaUsadas }


                    if (todasUsadas) {
                        Text(
                            text = "Ya se ha mostrado interés en el usuario en todas las ofertas disponibles.",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    val yaInvitada = ofertaSeleccionadaId in ofertasYaUsadas
                    ButtonGeneric(
                        text = "INTERESADO",
                        onClick = {
                            println("🟢 Botón INTERESADO pulsado con ID: $ofertaSeleccionadaId")

                            if (ofertaSeleccionadaId != null) {
                                viewModel.enviarInvitacion(
                                    idEmpresa = empresaId,
                                    idOferta = ofertaSeleccionadaId!!,
                                    idAlumno = idAlumno
                                )
                                Toast.makeText(context, "Interesado a la oferta", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Selecciona una oferta válida antes de continuar", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = ofertaSeleccionadaId != null && !yaInvitada // desactivado si no hay selección
                    )
                }
                val ofertasYaUsadas = viewModel.idsOfertasYaUsadas.collectAsState().value
                OfertaSeleccionDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    ofertas = listaOfertas.map { it.titulo },
                    deshabilitadas = listaOfertas
                        .filter { it.id?.toLong() in ofertasYaUsadas }
                        .map { it.titulo },
                    onSeleccion = { seleccionTitulo ->
                        val ofertaSeleccionada = listaOfertas.find { it.titulo == seleccionTitulo }
                        ofertaSeleccionadaTitulo = seleccionTitulo
                        ofertaSeleccionadaId = ofertaSeleccionada?.id?.toLong()
                    }
                )

            }
        }
    } ?: run {
        // Puedes mostrar un loading o error si lo deseas
        Text("Cargando...")
    }
}
package com.torre.b2c2c_tfg.ui.screens

import android.graphics.drawable.Icon
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
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.FiltroDropdown
import com.torre.b2c2c_tfg.ui.components.OfertaSeleccionDialog


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PerfilDetalleScreen(navController: NavController, idAlumno: Long, sessionViewModel: SessionViewModel) {
    val context = LocalContext.current
    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L
    var listaOfertas by remember { mutableStateOf<List<Oferta>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    var ofertaSeleccionada by remember { mutableStateOf<String?>("OFERTAS") }

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
        PerfilDetalleViewModel(GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))))
    }

    val alumno by viewModel.alumno.collectAsState()

    LaunchedEffect(idAlumno) {
        viewModel.cargarAlumno(idAlumno)
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
                         text = ofertaSeleccionada ?: "OFERTAS",
                         onClick = { showDialog = true }
                    )

                    ButtonGeneric(text = "INTERESADO", onClick = {
                        // TODO: Acción para marcar como interesado
                    })
                }
                OfertaSeleccionDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    ofertas = listaOfertas.map { it.titulo },
                    onSeleccion = { seleccion ->
                        ofertaSeleccionada = seleccion
                    }
                )

            }
        }
    } ?: run {
        // Puedes mostrar un loading o error si lo deseas
        Text("Cargando...")
    }
}
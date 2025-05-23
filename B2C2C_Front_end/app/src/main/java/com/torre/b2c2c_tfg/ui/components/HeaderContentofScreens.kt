package com.torre.b2c2c_tfg.ui.components

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel

@Composable
fun HeaderContentofScreens(
    sessionViewModel: SessionViewModel,
    viewModel: OfertasScreenViewModel,
    onFiltroSeleccionado: (String) -> Unit,
    navController: NavController
) {
    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value
    val alumno by viewModel.alumno.collectAsState()
    val empresa by viewModel.empresa.collectAsState()
    val filtroActual = viewModel.filtroSeleccionado.collectAsState().value

    val dropdownVisible = remember { mutableStateOf(false) }

    LaunchedEffect(userType) {
        if (userType == "alumno") {
            viewModel.cargarAlumno(userId)
            viewModel.cargarFiltros("alumno")
        } else if (userType == "empresa") {
            viewModel.cargarEmpresa(userId)
            viewModel.cargarFiltros("empresa")
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconMessage(onClick = {
                navController.navigate(ScreenRoutes.Notification)
            })
        }

        // Mostrar el perfil según tipo
        if (userType == "alumno" && alumno != null) {
            ProfileCard(
                imagenUri = RetrofitInstance.buildUri(alumno?.imagen),
                name = "${alumno!!.nombre} ${alumno!!.apellido}"
            )
        }

        if (userType == "empresa" && empresa != null) {
            ProfileCard(
                imagenUri = RetrofitInstance.buildUri(empresa?.imagen),
                name = empresa!!.nombre ?: ""
            )
        }

        // Icono de filtro y botón limpiar filtro
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        ) {
            IconFilter(onClick = { dropdownVisible.value = true })

            if (!filtroActual.isNullOrBlank()) {
                Button(
                    onClick = { viewModel.filtroSeleccionado.value = null },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Limpiar filtro")
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {

        }


        // Dropdown de opciones según tipo
        if (dropdownVisible.value) {
            val opciones = if (userType == "alumno") {
                viewModel.sectoresUnicos.collectAsState().value
            } else {
                viewModel.titulacionesUnicas.collectAsState().value
            }

            FiltroDropdown(
                expanded = true,
                onDismissRequest = { dropdownVisible.value = false },
                opciones = opciones,
                onSeleccion = {
                    dropdownVisible.value = false
                    onFiltroSeleccionado(it)
                }
            )
        }
    }
}

package com.torre.b2c2c_tfg.ui.components

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel

@Composable
fun HeaderContentofScreens (
    sessionViewModel: SessionViewModel,
    viewModel: OfertasScreenViewModel,
    onFiltroSeleccionado: (String) -> Unit
) {
    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value
    val alumno by viewModel.alumno.collectAsState()
    val empresa by viewModel.empresa.collectAsState()

    val dropdownAlumnoVisible = remember { mutableStateOf(false) }
    val dropdownEmpresaVisible = remember { mutableStateOf(false) }

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
            IconMessage(onClick = { /* mensajes */ })
        }

        if (userType == "alumno" && alumno != null) {
            ProfileCard(
                imageUrl = alumno!!.imagen ?: "",
                name = "${alumno!!.nombre} ${alumno!!.apellido}"
            )
            IconFilter(onClick = { dropdownAlumnoVisible.value = true })

            if (dropdownAlumnoVisible.value) {
                FiltroDropdown(
                    expanded = true,
                    onDismissRequest = { dropdownAlumnoVisible.value = false },
                    opciones = viewModel.sectoresUnicos.collectAsState().value,
                    onSeleccion = {
                        dropdownAlumnoVisible.value = false
                        onFiltroSeleccionado(it)
                    }
                )
            }
        }

        if (userType == "empresa" && empresa != null) {
            ProfileCard(
                imageUrl = empresa!!.imagen ?: "",
                name = empresa!!.nombre ?: ""
            )
            IconFilter(onClick = { dropdownEmpresaVisible.value = true })

            if (dropdownEmpresaVisible.value) {
                FiltroDropdown(
                    expanded = true,
                    onDismissRequest = { dropdownEmpresaVisible.value = false },
                    opciones = viewModel.titulacionesUnicas.collectAsState().value,
                    onSeleccion = {
                        dropdownEmpresaVisible.value = false
                        onFiltroSeleccionado(it)
                    }
                )
            }

        }
    }
}
package com.torre.b2c2c_tfg.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FiltroDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    opciones: List<String>,
    onSeleccion: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        opciones.forEach { opcion ->
            DropdownMenuItem(
                text = { Text(opcion) },
                onClick = {
                    onSeleccion(opcion)
                    onDismissRequest()
                }
            )
        }
    }
}

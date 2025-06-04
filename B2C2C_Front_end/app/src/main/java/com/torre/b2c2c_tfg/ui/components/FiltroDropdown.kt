package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

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
        // Aquí metemos scroll si hay muchas opciones
        val maxVisibleItems = 4
        val heightPerItem = 48.dp // cada item suele medir esto en Material3
        val maxHeight = maxVisibleItems * heightPerItem

        androidx.compose.foundation.layout.Box(
            modifier = Modifier.heightIn(max = maxHeight).verticalScroll(rememberScrollState())
        ) {
            Column {
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
    }
}

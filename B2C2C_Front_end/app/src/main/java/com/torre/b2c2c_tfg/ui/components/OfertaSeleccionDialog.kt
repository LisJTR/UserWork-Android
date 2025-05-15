package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun OfertaSeleccionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    ofertas: List<String>,
    onSeleccion: (String) -> Unit,
    deshabilitadas: List<String> = emptyList()
) {
    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {}, // no lo necesitamos
        title = { Text("Selecciona una oferta") },
        text = {
            Column {
                ofertas.forEach { titulo ->
                    val estaDeshabilitada = deshabilitadas.contains(titulo)
                    Text(
                        text = titulo,
                        color = if (estaDeshabilitada) Color.Gray else Color.Unspecified,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .let {
                                if (!estaDeshabilitada) {
                                    it.clickable {
                                        onSeleccion(titulo)
                                        onDismiss()
                                    }
                                } else {
                                    it
                                }
                            }
                    )
                }
            }
        }
    )
}

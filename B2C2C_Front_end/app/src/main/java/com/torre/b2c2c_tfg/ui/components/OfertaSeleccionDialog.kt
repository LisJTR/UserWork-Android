package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun OfertaSeleccionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    ofertas: List<String>,
    onSeleccion: (String) -> Unit
) {
    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {}, // no lo necesitamos
        title = { Text("Selecciona una oferta") },
        text = {
            Column {
                ofertas.forEach { titulo ->
                    Text(
                        text = titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                onSeleccion(titulo)
                                onDismiss()
                            }
                    )
                }
            }
        }
    )
}
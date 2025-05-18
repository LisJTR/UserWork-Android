package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun CambiarPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cambiar Contraseña") },
        text = {
            Column {
                OutlinedTextField(value = actual, onValueChange = { actual = it }, label = { Text("Contraseña actual") })
                OutlinedTextField(value = nueva, onValueChange = { nueva = it }, label = { Text("Nueva contraseña") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(actual, nueva)
                onDismiss()
            }) { Text("Confirmar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

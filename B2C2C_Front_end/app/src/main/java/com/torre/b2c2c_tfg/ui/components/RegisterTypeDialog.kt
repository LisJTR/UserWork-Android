package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Funcion que muestra la ventana flotante de registro
@Composable
fun RegisterTypeDialog(
    onDismiss: () -> Unit,
    onAlumnoClick: () -> Unit,
    onEmpresaClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconCloseButton(
                        onClick = onDismiss)
                }
                Text("¿Qué tipo de usuario eres?", style = MaterialTheme.typography.titleMedium)

                ButtonGeneric(
                    text = "ALUMNO",
                    onClick = onAlumnoClick,
                    modifier = Modifier.fillMaxWidth()
                )

                ButtonGeneric(
                    text = "EMPRESA",
                    onClick = onEmpresaClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

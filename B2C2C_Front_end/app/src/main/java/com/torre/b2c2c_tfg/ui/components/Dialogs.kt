package com.torre.b2c2c_tfg.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Función que muestra el diálogo de inicio de sesión
@Composable
fun LoginDialog(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit
) {
    AlertDialog(
        // Función que se ejecuta cuando el usuario intenta cerrar el diálogo
        // (ya sea tocando fuera de la ventana o presionando el botón "Atrás")
        onDismissRequest = onDismiss,

        // ConfirmButton y DismissButton se dejan vacíos porque hemos incluido
        // nuestro propio botón personalizado dentro del contenido del diálogo (text)
        confirmButton = {},
        dismissButton = {},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconCloseButton(
                        onClick = onDismiss)
                }
                TextTitle("Iniciar sesión")

                OutlinedInputTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    label = "Correo o usuario",
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedInputTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                ButtonGeneric(
                    text = "Iniciar",
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }
        }
    )
}

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

package com.torre.b2c2c_tfg.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.torre.b2c2c_tfg.ui.components.*

//Función que muestra el diálogo de inicio de sesión
@Composable
fun LoginDialog(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    mensajeErrorLocal: String? = null
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
                var passwordVisible by remember { mutableStateOf(false) }
                OutlinedInputTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = "Toggle password visibility")
                        }


                    }
                )
                if (!mensajeErrorLocal.isNullOrBlank()) {
                    Text(
                        text = mensajeErrorLocal,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
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


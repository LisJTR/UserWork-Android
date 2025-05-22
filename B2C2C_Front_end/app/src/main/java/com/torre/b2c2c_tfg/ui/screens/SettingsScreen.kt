package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.ButtonSettingItem
import com.torre.b2c2c_tfg.ui.components.ScreenTitle
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.torre.b2c2c_tfg.ui.components.CambiarPasswordDialog
import com.torre.b2c2c_tfg.ui.components.ConfirmDialog
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SettingsScreenViewModel




@Composable
fun SettingsScreen(
    settingsViewModel: SettingsScreenViewModel,
    onToggleTheme: () -> Unit,
    onChangePassword: () -> Unit,
    onDeleteAccount: () -> Unit,
    navController: NavController
) {

    // Logica para mostrar el diálogo de confirmación de cierre de sesión
    val showLogoutDialogState = remember { mutableStateOf(false) }
    val navigateToWelcome by settingsViewModel.navigateToWelcome.collectAsState()
    // Diálogo para cambiar el tema
    val showToggleThemeDialog = remember { mutableStateOf(false) }

    if (navigateToWelcome) {
        // Navega y resetea el flag
        navController.navigate(ScreenRoutes.Welcome) { popUpTo(0) }
        settingsViewModel.resetNavigationFlag()
    }

    if (showLogoutDialogState.value) {
        ConfirmDialog(
            title = "Cerrar sesión",
            message = "¿Estás seguro de que deseas cerrar sesión?",
            onConfirm = {
                showLogoutDialogState.value = false
                settingsViewModel.logout()
            },
            onDismiss = { showLogoutDialogState.value = false }
        )
    }

    // Mensaje de eliminación de cuenta
    val context = LocalContext.current
    val mensajeEliminacion by settingsViewModel.mensajeEliminacion.collectAsState()

    if (mensajeEliminacion != null) {
        LaunchedEffect(mensajeEliminacion) {
            Toast.makeText(context, mensajeEliminacion, Toast.LENGTH_SHORT).show()
            settingsViewModel.resetMensaje()
        }
    }

    //  Mensaje de cambio de contraseña
    val mensajePassword by settingsViewModel.mensajeCambioPassword.collectAsState()

    if (mensajePassword != null) {
        LaunchedEffect(mensajePassword) {
            Toast.makeText(context, mensajePassword, Toast.LENGTH_SHORT).show()
            settingsViewModel.resetMensajePassword()
        }
    }

    // Diálogo para cambiar el tema
    if (showToggleThemeDialog.value) {
        ConfirmDialog(
            title = "Cambiar tema",
            message = "¿Quieres cambiar el tema de la aplicación?",
            onConfirm = {
                showToggleThemeDialog.value = false
                onToggleTheme()
            },
            onDismiss = { showToggleThemeDialog.value = false }
        )
    }

    // Diálogo para eliminar cuenta
    val showDeleteDialog = remember { mutableStateOf(false) }

    if (showDeleteDialog.value) {
        ConfirmDialog(
            title = "Eliminar cuenta",
            message = "¿Estás seguro de que deseas eliminar tu cuenta? Esta acción es irreversible.",
            onConfirm = {
                showDeleteDialog.value = false
                settingsViewModel.eliminarCuenta()
            },
            onDismiss = { showDeleteDialog.value = false }
        )
    }

    // Diálogo para cambiar contraseña
    val showChangePasswordDialog = remember { mutableStateOf(false) }

    if (showChangePasswordDialog.value) {
        CambiarPasswordDialog(
            onDismiss = { showChangePasswordDialog.value = false },
            onConfirm = { actual, nueva ->
                settingsViewModel.cambiarPassword(actual, nueva)
            }
        )
    }

    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName

    // Mensaje de version sacada del build.gradle
    val showVersionDialog = remember { mutableStateOf(false) }
    if (showVersionDialog.value) {
        AlertDialog(
            onDismissRequest = { showVersionDialog.value = false },
            title = { Text("Información de la app") },
            text = {
                Column {
                    Text("Versión: $versionName")
                    Text("TFG - 2025")
                }
            },
            confirmButton = {
                TextButton(onClick = { showVersionDialog.value = false }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "AJUSTES",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        ButtonSettingItem(
            text = "Cerrar Sesión",
            onClick =  { showLogoutDialogState.value= true })
        ButtonSettingItem(
            text = "Cambiar Tema",
            onClick = { showToggleThemeDialog.value = true })
        ButtonSettingItem(
            text = "Cambiar Contraseña",
            onClick = { showChangePasswordDialog.value = true })
        ButtonSettingItem(
            text = "Eliminar Cuenta",
            onClick = { showDeleteDialog.value = true })
        ButtonSettingItem(
            text = "Información sobre versión",
            onClick = { showVersionDialog.value = true })
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun SettingsScreen() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            SettingsScreen( )
        }
    }
}

package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.CambiarPasswordRequest
import com.torre.b2c2c_tfg.domain.usecase.CambiarPasswordUseCase
import com.torre.b2c2c_tfg.domain.usecase.EliminarCuentaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SettingsScreenViewModel(
    private val sessionViewModel: SessionViewModel,
    private val eliminarCuentaUseCase: EliminarCuentaUseCase,
    private val cambiarPasswordUseCase: CambiarPasswordUseCase
) : ViewModel() {

    private val _navigateToWelcome = MutableStateFlow(false)
    val navigateToWelcome: StateFlow<Boolean> = _navigateToWelcome

    private val _mensajeEliminacion = MutableStateFlow<String?>(null)
    val mensajeEliminacion: StateFlow<String?> = _mensajeEliminacion

    val mensajeCambioPassword = MutableStateFlow<String?>(null)

    fun logout() {
        sessionViewModel.clearSession()
        _navigateToWelcome.value = true
    }

    fun resetNavigationFlag() {
        _navigateToWelcome.value = false
    }

    fun eliminarCuenta() {
        val userId = sessionViewModel.userId.value
        val tipo = sessionViewModel.userType.value

        if (userId == null || tipo == null) return

        viewModelScope.launch {
            try {
                if (tipo == "alumno") {
                    eliminarCuentaUseCase.eliminarAlumno(userId)
                } else {
                    eliminarCuentaUseCase.eliminarEmpresa(userId)
                }
                sessionViewModel.clearSession()
                _mensajeEliminacion.value = "Cuenta eliminada correctamente"
                _navigateToWelcome.value = true
            } catch (e: Exception) {
              // aquí puedes meter un estado de error si quieres
                _mensajeEliminacion.value = "Error al eliminar la cuenta: ${e.message}"
            }
        }
    }

    fun cambiarPassword(passwordActual: String, passwordNueva: String) {
        val id = sessionViewModel.userId.value ?: return
        val tipo = sessionViewModel.userType.value ?: return

        val request = CambiarPasswordRequest(
            id = id,
            tipo = tipo,
            passwordActual = passwordActual,
            passwordNueva = passwordNueva
        )

        viewModelScope.launch {
            try {
                val response = cambiarPasswordUseCase.cambiarPassword(request)
                if (response.isSuccessful) {
                    mensajeCambioPassword.value = "Contraseña cambiada con éxito"
                } else {
                    val errorMsg =
                        response.errorBody()?.string() ?: "No se pudo cambiar la contraseña"
                    mensajeCambioPassword.value = "Error: $errorMsg"
                }
            } catch (e: Exception) {
                mensajeCambioPassword.value = "Error de red: ${e.message}"
            }
        }
    }


    fun resetMensaje() {
        _mensajeEliminacion.value = null
    }

    fun resetMensajePassword() {
        mensajeCambioPassword.value = null
    }

}
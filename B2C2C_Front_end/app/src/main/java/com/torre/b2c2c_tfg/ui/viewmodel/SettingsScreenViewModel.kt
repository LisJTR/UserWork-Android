package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.domain.usecase.EliminarCuentaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class SettingsScreenViewModel(
    private val sessionViewModel: SessionViewModel,
    private val eliminarCuentaUseCase: EliminarCuentaUseCase
) : ViewModel() {

    private val _navigateToWelcome = MutableStateFlow(false)
    val navigateToWelcome: StateFlow<Boolean> = _navigateToWelcome

    private val _mensajeEliminacion = MutableStateFlow<String?>(null)
    val mensajeEliminacion: StateFlow<String?> = _mensajeEliminacion



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

    fun resetMensaje() {
        _mensajeEliminacion.value = null
    }

}
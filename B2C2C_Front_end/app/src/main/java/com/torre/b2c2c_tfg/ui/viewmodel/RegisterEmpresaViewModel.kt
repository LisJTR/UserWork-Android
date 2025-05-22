package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.domain.usecase.CreateEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateEmpresaUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterEmpresaViewModel(
    private val getEmpresaUseCase: GetEmpresaUseCase,
    private val updateEmpresaUseCase: UpdateEmpresaUseCase,
    private val createEmpresaUseCase: CreateEmpresaUseCase
) : ViewModel() {

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa

    var empresaId: Long? = null
        private set

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    fun cargarDatos(id: Long) {
        viewModelScope.launch {
            try {
                _empresa.value = getEmpresaUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun guardarDatos(empresa: Empresa, esEdicion: Boolean, onEmpresaGuardada: (Empresa?) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val resultado = if (esEdicion) {
                    updateEmpresaUseCase(empresa)
                } else {
                    createEmpresaUseCase(empresa)
                }

                _empresa.value = resultado
                empresaId = resultado?.id?.toLong()
                _mensajeError.value = ""
                onEmpresaGuardada(resultado) // ← devuelves la empresa
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 409) {
                    _mensajeError.value = "El usuario o correo electrónico ya están en uso"
                } else {
                    _mensajeError.value = "Error del servidor: ${e.code()}"
                }
                onEmpresaGuardada(null)
            } catch (e: Exception) {
                _mensajeError.value = "Error inesperado: ${e.message}"
                onEmpresaGuardada(null)
            }
        }
    }

    fun limpiarError() {
        _mensajeError.value = null
    }

}


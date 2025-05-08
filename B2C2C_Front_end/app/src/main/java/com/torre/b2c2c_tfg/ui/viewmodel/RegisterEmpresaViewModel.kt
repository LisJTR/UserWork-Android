package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateEmpresaUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterEmpresaViewModel(
    private val getEmpresaUseCase: GetEmpresaUseCase,
    private val updateEmpresaUseCase: UpdateEmpresaUseCase
) : ViewModel() {

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa

    fun cargarDatos(id: Long) {
        viewModelScope.launch {
            try {
            _empresa.value = getEmpresaUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun guardarDatos(empresa: Empresa) {
        viewModelScope.launch {
            updateEmpresaUseCase(empresa)
            // Puedes emitir estado o mostrar feedback
        }
    }
}

package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var empresaId: Long? by mutableStateOf(null)
        private set


    fun cargarDatos(id: Long) {
        viewModelScope.launch {
            try {
            _empresa.value = getEmpresaUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun crearEmpresa(empresa: Empresa, onEmpresaCreada: (Empresa) -> Unit) {
        viewModelScope.launch {
            val empresaCreadaNullable = createEmpresaUseCase(empresa)
            empresaCreadaNullable?.let { empresaCreada ->
                empresaId = empresaCreada.id?.toLong()
                onEmpresaCreada(empresaCreada)
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

package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfertasViewModel (
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val getEmpresaUseCase: GetEmpresaUseCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa

    fun cargarAlumno(id: Long) {
        viewModelScope.launch {
            _alumno.value = getAlumnoUseCase(id)
        }
    }

    fun cargarEmpresa(id: Long) {
        viewModelScope.launch {
            _empresa.value = getEmpresaUseCase(id)
        }
    }
}

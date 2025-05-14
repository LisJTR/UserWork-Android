package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilDetalleViewModel(private val getAlumnoUseCase: GetAlumnoUseCase) : ViewModel() {
    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    fun cargarAlumno(id: Long) {
        viewModelScope.launch {
            _alumno.value = getAlumnoUseCase(id)
        }
    }
}
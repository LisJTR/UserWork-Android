package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateAlumnoUserCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterAlumnoViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val updateAlumnoUserCase: UpdateAlumnoUserCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    fun cargarDatos() {
        viewModelScope.launch {
            val datos = getAlumnoUseCase()
            _alumno.value = datos
        }
    }

    fun guardarDatos(alumno: Alumno) {
        viewModelScope.launch {
            updateAlumnoUserCase(alumno)
            _alumno.value = alumno
        }
    }
}
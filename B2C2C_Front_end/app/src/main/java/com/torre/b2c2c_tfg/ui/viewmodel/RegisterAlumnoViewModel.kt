package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateAlumnoUserCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.domain.usecase.CreateAlumnoUseCase

class RegisterAlumnoViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val updateAlumnoUserCase: UpdateAlumnoUserCase,
    private val createAlumnoUseCase: CreateAlumnoUseCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    var alumnoId: Long? = null
        private set

    fun cargarDatos(id: Long) {
        viewModelScope.launch {
            try {
            _alumno.value = getAlumnoUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun guardarDatos(alumno: Alumno, esEdicion: Boolean) {
        viewModelScope.launch {
            try {
                println("GUARDANDO ALUMNO...")
                val resultado = if (esEdicion) {
                    updateAlumnoUserCase(alumno)
                } else {
                    createAlumnoUseCase(alumno)
                }

                println("RESULTADO GUARDADO: ${resultado.id}")
                _alumno.value = resultado
                alumnoId = resultado.id?.toLong()
            } catch (e: Exception) {
                println("ERROR al guardar: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
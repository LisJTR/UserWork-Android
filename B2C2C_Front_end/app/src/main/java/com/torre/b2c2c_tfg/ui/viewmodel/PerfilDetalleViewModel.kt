package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.domain.usecase.CrearInvitacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilDetalleViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val crearInvitacionUseCase: CrearInvitacionUseCase
)
    : ViewModel() {
    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    fun cargarAlumno(id: Long) {
        viewModelScope.launch {
            _alumno.value = getAlumnoUseCase(id)
        }
    }

    fun enviarInvitacion(idEmpresa: Long, idOferta: Long, idAlumno: Long) {
        viewModelScope.launch {
            println("🔹 Enviando invitación - empresaId: $idEmpresa, ofertaId: $idOferta, alumnoId: $idAlumno")
            val success = crearInvitacionUseCase(
                Invitacion(
                    empresaId = idEmpresa,
                    alumnoId = idAlumno,
                    ofertaId = idOferta
                )
            )
            println("📨 Resultado de la invitación: $success")
        }
    }

}
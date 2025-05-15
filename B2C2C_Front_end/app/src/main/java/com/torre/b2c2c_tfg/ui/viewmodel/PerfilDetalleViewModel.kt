package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.domain.usecase.CrearInvitacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase


class PerfilDetalleViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val crearInvitacionUseCase: CrearInvitacionUseCase,
    private val crearNotificacionUseCase: CrearNotificacionUseCase,
    private val getInvitacionesPorEmpresaUseCase: GetInvitacionPorEmpresaUseCase
)
    : ViewModel() {
    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    private val _idsOfertasYaUsadas = MutableStateFlow<List<Long>>(emptyList())
    val idsOfertasYaUsadas: StateFlow<List<Long>> = _idsOfertasYaUsadas

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

            if (success) {
                val result = crearNotificacionUseCase(
                    Notificacion(
                        tipo = "invitacion",
                        mensaje = "Una empresa está interesada en ti.",
                        empresaId = idEmpresa,
                        ofertaId = idOferta,
                        alumnoId = idAlumno,
                        destinatarioTipo = "alumno"
                    )
                )
                println("✅ ¿Se creó la notificación? $result")
            }

        }
    }

    fun cargarInvitacionesEnviadas(empresaId: Long, alumnoId: Long) {
        viewModelScope.launch {
            val todas = getInvitacionesPorEmpresaUseCase(empresaId)
            _idsOfertasYaUsadas.value = todas
                .filter { it.alumnoId == alumnoId }
                .mapNotNull { it.ofertaId?.toLong() }
        }
    }



}
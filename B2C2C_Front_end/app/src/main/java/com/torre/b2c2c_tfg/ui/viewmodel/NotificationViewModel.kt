package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
import com.torre.b2c2c_tfg.ui.util.FiltroNotificacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificacionesPorAlumnoUseCase: GetNotificacionesPorAlumnoUseCase,
    private val getNotificacionesPorEmpresaUseCase: GetNotificacionesPorEmpresaUseCase,
    private val actualizarNotificacionUseCase: ActualizarNotificacionUseCase
) : ViewModel() {

    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones
    val filtroActual = MutableStateFlow(FiltroNotificacion.TODAS)
    val notificacionesFiltradas = notificaciones.combine(filtroActual) { lista, filtro ->
        when (filtro) {
            FiltroNotificacion.TODAS -> lista
            FiltroNotificacion.RESPONDIDAS -> lista.filter {
                it.estadoRespuesta != null && it.estadoRespuesta != "pendiente"
            }
            FiltroNotificacion.PENDIENTES -> lista.filter {
                it.estadoRespuesta == null || it.estadoRespuesta == "pendiente"
            }
        }
    }

    fun cargarNotificaciones(userId: Long, userType: String) {
        viewModelScope.launch {
            _notificaciones.value = when (userType) {
                "alumno" -> getNotificacionesPorAlumnoUseCase(userId)
                "empresa" -> getNotificacionesPorEmpresaUseCase(userId)
                else -> emptyList()
            }
        }
    }

    fun marcarComoLeida(notificacion: Notificacion) {
        viewModelScope.launch {
            val exito = actualizarNotificacionUseCase.marcarComoLeida(notificacion.id?.toLong() ?: return@launch)
            if (exito) {
                val userType = notificacion.destinatarioTipo
                val id = notificacion.alumnoId ?: notificacion.empresaId ?: return@launch
                cargarNotificaciones(id, userType)
            }
        }
    }

}
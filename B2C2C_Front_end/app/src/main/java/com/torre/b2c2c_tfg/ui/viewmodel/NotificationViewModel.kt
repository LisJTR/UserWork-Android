package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificacionesPorAlumnoUseCase: GetNotificacionesPorAlumnoUseCase,
    private val getNotificacionesPorEmpresaUseCase: GetNotificacionesPorEmpresaUseCase,
    private val actualizarNotificacionUseCase: ActualizarNotificacionUseCase
) : ViewModel() {

    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

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
            val actualizada = notificacion.copy(
                leido = true,
                estadoRespuesta = notificacion.estadoRespuesta ?: "pendiente"
            )
            actualizarNotificacionUseCase(actualizada)
            // Opcional: recarga la lista si quieres ver el cambio reflejado
            val userType = actualizada.destinatarioTipo
            val id = actualizada.alumnoId ?: actualizada.empresaId ?: return@launch
            cargarNotificaciones(id, userType)
        }
    }
}
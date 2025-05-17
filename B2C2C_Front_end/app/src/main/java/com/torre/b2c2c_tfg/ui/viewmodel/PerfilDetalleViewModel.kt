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
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEstadoRespuestaPorIdUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionPorIdUseCase


class PerfilDetalleViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val crearInvitacionUseCase: CrearInvitacionUseCase,
    private val crearNotificacionUseCase: CrearNotificacionUseCase,
    private val getInvitacionesPorEmpresaUseCase: GetInvitacionPorEmpresaUseCase,
    private val actualizarNotificacionUseCase: ActualizarNotificacionUseCase,
    private val getNotificacionPorIdUseCase: GetNotificacionPorIdUseCase
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

    private val _estadoRespuesta = MutableStateFlow<String?>(null)
    val estadoRespuesta: StateFlow<String?> = _estadoRespuesta

    private val _tipoNotificacion = MutableStateFlow<String?>(null)
    val tipoNotificacion: StateFlow<String?> = _tipoNotificacion

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

    fun responderNotificacion(idNotificacion: Long, estadoRespuesta: String) {
        viewModelScope.launch {
            val exito = actualizarNotificacionUseCase(idNotificacion, estadoRespuesta)
            if (exito) {
                // Obtener la notificación original
                val original = getNotificacionPorIdUseCase(idNotificacion)

                // Crear mensaje personalizado según respuesta
                val mensaje = when (estadoRespuesta) {
                    "inter_mutuo" -> "El alumno ha mostrado interés mutuo en tu oferta."
                    "no_interesado" -> "El alumno no está interesado en tu oferta."
                    "seleccionado" -> "Has sido seleccionado para la oferta."
                    "descartado" -> "No has sido seleccionado para la oferta."
                    else -> null
                }

                // Crear notificación de respuesta
                mensaje?.let {
                    val nuevaNoti = Notificacion(
                        tipo = "respuesta",
                        mensaje = it,
                        alumnoId = original?.alumnoId,
                        empresaId = original?.empresaId,
                        ofertaId = original?.ofertaId,
                        destinatarioTipo = if (original?.destinatarioTipo == "empresa") "alumno" else "empresa",
                        estadoRespuesta = estadoRespuesta
                    )
                    crearNotificacionUseCase(nuevaNoti)
                }

                println("✅ Notificación actualizada y respuesta enviada")
            } else {
                println("❌ Error al actualizar estado de la notificación")
            }
        }
    }


    fun cargarEstadoRespuesta(idNotificacion: Long) {
        viewModelScope.launch {
            println("🔍 Buscando notificación con ID: $idNotificacion")
            val notificacion = getNotificacionPorIdUseCase(idNotificacion)
            println("📬 Estado encontrado: ${notificacion?.estadoRespuesta}")
            _estadoRespuesta.value = notificacion?.estadoRespuesta
            _tipoNotificacion.value = notificacion?.tipo
        }
    }


}
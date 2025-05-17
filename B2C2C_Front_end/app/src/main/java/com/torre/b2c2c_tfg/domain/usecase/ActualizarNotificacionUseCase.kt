package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.repository.NotificacionRepository

class ActualizarNotificacionUseCase(private val repository: NotificacionRepository) {
    suspend operator fun invoke(id: Long, estado: String): Boolean {
        return try {
            repository.actualizarEstadoRespuesta(id, estado)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun marcarComoLeida(id: Long): Boolean {
        return try {
            repository.marcarNotificacionComoLeida(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
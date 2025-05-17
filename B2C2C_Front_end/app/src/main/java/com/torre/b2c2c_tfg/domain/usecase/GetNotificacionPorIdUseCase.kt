package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.repository.NotificacionRepository

class GetNotificacionPorIdUseCase(private val repository: NotificacionRepository) {
    suspend operator fun invoke(id: Long): Notificacion? {
        return repository.getNotificacionPorId(id)
    }
}

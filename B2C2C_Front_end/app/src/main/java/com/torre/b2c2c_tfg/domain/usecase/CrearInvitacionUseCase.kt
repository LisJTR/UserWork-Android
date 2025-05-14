package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.domain.repository.InvitacionRepository

class CrearInvitacionUseCase(private val repository: InvitacionRepository) {
    suspend operator fun invoke(invitacion: Invitacion): Boolean {
        return repository.crearInvitacion(invitacion)
    }
}
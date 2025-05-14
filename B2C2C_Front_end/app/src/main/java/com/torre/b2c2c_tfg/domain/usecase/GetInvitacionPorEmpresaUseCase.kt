package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.domain.repository.InvitacionRepository

class GetInvitacionPorEmpresaUseCase(private val repository: InvitacionRepository) {
    suspend operator fun invoke(empresaId: Long): List<Invitacion> {
        return repository.getInvitacionesByEmpresaId(empresaId)
    }
}
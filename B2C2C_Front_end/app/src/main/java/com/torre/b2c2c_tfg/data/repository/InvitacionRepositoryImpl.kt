package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.InvitacionRepository

class InvitacionRepositoryImpl(private val api: ApiService) : InvitacionRepository {
    override suspend fun crearInvitacion(invitacion: Invitacion): Boolean {
        return try {
            api.crearInvitacion(invitacion).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getInvitacionesByEmpresaId(empresaId: Long): List<Invitacion> {
        return try {
            api.getInvitacionesByEmpresaId(empresaId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
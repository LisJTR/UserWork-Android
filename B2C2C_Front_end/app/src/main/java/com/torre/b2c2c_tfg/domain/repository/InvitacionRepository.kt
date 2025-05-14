package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.Invitacion

interface InvitacionRepository {
    suspend fun crearInvitacion(invitacion: Invitacion): Boolean
    suspend fun getInvitacionesByEmpresaId(empresaId: Long): List<Invitacion>
}
package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.Notificacion

interface NotificacionRepository {
    suspend fun crearNotificacion(notificacion: Notificacion): Boolean
    suspend fun getNotificacionesPorAlumno(id: Long): List<Notificacion>
    suspend fun getNotificacionesPorEmpresa(id: Long): List<Notificacion>
    suspend fun actualizarNotificacion(notificacion: Notificacion): Boolean
    suspend fun actualizarEstadoRespuesta(id: Long, estado: String): Boolean
    suspend fun marcarNotificacionComoLeida(id: Long): Boolean
    suspend fun getNotificacionPorId(id: Long): Notificacion?

}
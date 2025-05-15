package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.NotificacionRepository

class NotificacionRepositoryImpl(private val api: ApiService) : NotificacionRepository {
    override suspend fun crearNotificacion(notificacion: Notificacion): Boolean {
        return try {
            val response = api.crearNotificacion(notificacion)
            println("🔄 Código respuesta notificación: ${response.code()}")
            response.isSuccessful
        } catch (e: Exception) {
            println("❌ Error creando notificación: ${e.message}")
            false
        }
    }

    override suspend fun getNotificacionesPorAlumno(id: Long): List<Notificacion> {
        return try {
            api.getNotificacionesPorAlumno(id)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getNotificacionesPorEmpresa(id: Long): List<Notificacion> {
        return try {
            api.getNotificacionesPorEmpresa(id)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun actualizarNotificacion(notificacion: Notificacion): Boolean {
        return try {
            api.actualizarNotificacion(notificacion)
            true
        } catch (e: Exception) {
            false
        }
    }


}
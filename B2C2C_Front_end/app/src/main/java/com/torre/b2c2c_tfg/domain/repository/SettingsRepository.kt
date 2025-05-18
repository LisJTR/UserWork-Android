package com.torre.b2c2c_tfg.domain.repository

import retrofit2.Response

interface SettingsRepository {
    suspend fun eliminarAlumno(id: Long): Response<Unit>
    suspend fun eliminarEmpresa(id: Long): Response<Unit>

}
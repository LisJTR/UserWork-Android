package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.CambiarPasswordRequest
import com.torre.b2c2c_tfg.data.remote.ApiService
import retrofit2.Response

class SettingsRepository(private val apiService: ApiService) {

    suspend fun eliminarAlumno(id: Long): Response<Unit> = apiService.eliminarAlumno(id)
    suspend fun eliminarEmpresa(id: Long): Response<Unit> = apiService.eliminarEmpresa(id)
    suspend fun cambiarPassword(request: CambiarPasswordRequest): Response<Unit> {
        return apiService.cambiarPassword(request)
    }

}
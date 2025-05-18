package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.remote.ApiService
import retrofit2.Response

class SettingsRepository(private val apiService: ApiService) {

    suspend fun eliminarAlumno(id: Long): Response<Unit> = apiService.eliminarAlumno(id)
    suspend fun eliminarEmpresa(id: Long): Response<Unit> = apiService.eliminarEmpresa(id)
}
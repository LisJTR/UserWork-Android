package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import retrofit2.Response

// Interfaz que define la lógica del repositorio de login
// Separamos el dominio de la capa de datos
interface LoginRepository {

    suspend fun login(request: LoginRequest): Response<LoginResponse>
}
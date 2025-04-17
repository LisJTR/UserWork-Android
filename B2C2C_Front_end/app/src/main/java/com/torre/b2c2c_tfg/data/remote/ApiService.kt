package com.torre.b2c2c_tfg.data.remote

import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Interfaz que define las llamadas HTTP al back-end
// Retrofit necesita esta interfaz para construir las peticiones
interface ApiService {
    @POST("/api/login") // Ajustar el endpoint según el backend
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
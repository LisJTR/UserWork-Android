package com.torre.b2c2c_tfg.data.remote

import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.data.model.Oferta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// Interfaz que define las llamadas HTTP al back-end
// Retrofit necesita esta interfaz para construir las peticiones
interface ApiService {
    @POST("/api/login") // Ajustar el endpoint según el backend
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("empresa/perfil")
    suspend fun getEmpresa(): Empresa

    @PUT("empresa/perfil")
    suspend fun updateEmpresa(@Body empresa: Empresa): Response<Unit>

    @POST("oferta")
    suspend fun crearOferta(@Body oferta: Oferta): Response<Unit>

}
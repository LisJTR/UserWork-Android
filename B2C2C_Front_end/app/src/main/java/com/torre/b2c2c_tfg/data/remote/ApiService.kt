package com.torre.b2c2c_tfg.data.remote

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.data.model.Oferta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Interfaz que define las llamadas HTTP al back-end
// Retrofit necesita esta interfaz para construir las peticiones
interface ApiService {

    // Peticiones HTTP de Login
    @POST("/api/login") // Ajustar el endpoint según el backend
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Empresa
    @GET("empresa/perfil")
    suspend fun getEmpresa(): Empresa

    @PUT("empresa/perfil")
    suspend fun updateEmpresa(@Body empresa: Empresa): Response<Unit>

    @POST("empresa")
    suspend fun crearEmpresa(@Body empresa: Empresa): Response<Unit>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Ofertas (Empresa)
    @POST("oferta")
    suspend fun crearOferta(@Body oferta: Oferta): Response<Unit>

    @GET("oferta/perfil")
    suspend fun getOfertas(): List<Oferta>

    @PUT("oferta/{id}")
    suspend fun updateOferta(
        @Path("id") id: Int,
        @Body oferta: Oferta
    ): Response<Unit>

    @DELETE("oferta/{id}")
    suspend fun deleteOfertasDesdePerfil(
        @Path("id") id: Int
    ): Response<Unit>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Alumno

    @POST("alumno")
    suspend fun crearAlumno(@Body alumno: Alumno): Response<Unit>

    @GET("alumno/perfil")
    suspend fun getAlumno(): Alumno

    @PUT("alumno/perfil")
    suspend fun updateAlumno(@Body alumno: Alumno): Response<Unit>


}
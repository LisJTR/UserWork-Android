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
    @POST("/api/auth/login") // Ajustar el endpoint según el backend
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Empresa
    @GET("api/empresa/perfil")
    suspend fun getEmpresa(): Empresa

    @PUT("api/empresa/perfil")
    suspend fun updateEmpresa(@Body empresa: Empresa): Response<Unit>

    @GET("api/empresa/{id}")
    suspend fun getEmpresaById(@Path("id") id: Long): Empresa

    @POST("api/empresa")
    suspend fun crearEmpresa(@Body empresa: Empresa): Response<Unit>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Alumno

    @POST("api/alumno")
    suspend fun crearAlumno(@Body alumno: Alumno): Response<Unit>

    @GET("api/alumno/perfil")
    suspend fun getAlumno(): Alumno

    @GET("api/alumno/{id}")
    suspend fun getAlumnoById(@Path("id") id: Long): Alumno

    @PUT("api/alumno/perfil")
    suspend fun updateAlumno(@Body alumno: Alumno): Response<Unit>


    // -------------------------------------------------------------------
    // Peticiones HTTP de Ofertas (Empresa)
    @POST("api/oferta")
    suspend fun crearOferta(@Body oferta: Oferta): Response<Unit>

    @GET("api/oferta/perfil")
    suspend fun getOfertas(): List<Oferta>

    @PUT("api/oferta/{id}")
    suspend fun updateOferta(
        @Path("id") id: Int,
        @Body oferta: Oferta
    ): Response<Unit>

    @DELETE("api/oferta/{id}")
    suspend fun deleteOfertasDesdePerfil(
        @Path("id") id: Int
    ): Response<Unit>

    // -------------------------------------------------------------------


}
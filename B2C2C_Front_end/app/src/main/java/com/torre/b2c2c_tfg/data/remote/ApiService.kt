package com.torre.b2c2c_tfg.data.remote

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.AplicacionOferta
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.data.model.Oferta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun crearEmpresa(@Body empresa: Empresa): Response<Empresa>

    @GET("api/empresa/sectores")
    suspend fun getSectoresUnicos(): List<String>

    @GET("api/empresa")
    suspend fun getAllEmpresas(): List<Empresa>

    // -------------------------------------------------------------------
    // Peticiones HTTP de Alumno

    @POST("api/alumno")
    suspend fun crearAlumno(@Body alumno: Alumno): Response<Alumno>

    @GET("api/alumno/perfil")
    suspend fun getAlumno(): Alumno

    @GET("api/alumno/{id}")
    suspend fun getAlumnoById(@Path("id") id: Long): Alumno

    @PUT("api/alumno/perfil")
    suspend fun updateAlumno(@Body alumno: Alumno): Response<Alumno>

    @GET("api/alumno/titulaciones")
    suspend fun getTitulacionesUnicas(): List<String>

    @GET("api/alumno")
    suspend fun getAllAlumnos(): List<Alumno>


    // -------------------------------------------------------------------
    // Peticiones HTTP de Ofertas (Empresa)
    @POST("api/oferta")
    suspend fun crearOferta(@Body oferta: Oferta): Response<Unit>

    @GET("api/oferta/perfil")
    suspend fun getOfertasPerfil(): List<Oferta>

    @PUT("api/oferta/{id}")
    suspend fun updateOferta(
        @Path("id") id: Int,
        @Body oferta: Oferta
    ): Response<Unit>

    @DELETE("api/oferta/{id}")
    suspend fun deleteOfertasDesdePerfil(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("api/oferta/empresa/{id}")
    suspend fun getOfertasByEmpresaId(@Path("id") empresaId: Long): List<Oferta>

    @GET("api/oferta/todas")
    suspend fun getOfertas(): List<Oferta>

    @GET("api/oferta/{id}")
    suspend fun getOfertaById(@Path("id") id: Long): Oferta

    // -------------------------------------------------------------------
    // Peticiones HTTP de Aplicaciones (Alumno)

    @POST("api/aplicacion")
    suspend fun crearAplicacion(@Body aplicacion: AplicacionOferta): Response<Unit>


    @GET("api/aplicacion/alumno/{alumnoId}")
    suspend fun getAplicacionesPorAlumnoId(@Path("alumnoId") alumnoId: Long): List<AplicacionOferta>

    @GET("api/aplicacion/existe")
    suspend fun existeAplicacion(
        @Query("alumnoId") alumnoId: Long,
        @Query("ofertaId") ofertaId: Long
    ): Boolean

    // -------------------------------------------------------------------
    // Peticiones HTTP de Invitaciones (Empresa)

    @POST("api/invitacion")
    suspend fun crearInvitacion(@Body invitacion: Invitacion): Response<Unit>

    @GET("api/invitacion/empresa/{empresaId}")
    suspend fun getInvitacionesByEmpresaId(@Path("empresaId") empresaId: Long): List<Invitacion>


    // -------------------------------------------------------------------
    // Peticiones HTTP de Notificaciones (Alumno y Empresa)

    @POST("api/notificacion")
    suspend fun crearNotificacion(@Body notificacion: Notificacion): Response<Unit>

    @GET("api/notificacion/alumno/{id}")
    suspend fun getNotificacionesPorAlumno(@Path("id") id: Long): List<Notificacion>

    @GET("api/notificacion/empresa/{id}")
    suspend fun getNotificacionesPorEmpresa(@Path("id") id: Long): List<Notificacion>

    @PUT("api/notificacion")
    suspend fun actualizarNotificacion(@Body notificacion: Notificacion)

    @PUT("api/notificacion/{id}/{estado}")
    suspend fun actualizarEstadoRespuesta(
        @Path("id") id: Long,
        @Path("estado") estado: String
    ): Response<Notificacion>

    @PUT("api/notificacion/{id}/marcar-leida")
    suspend fun marcarNotificacionComoLeida(@Path("id") id: Long): Response<Notificacion>



}
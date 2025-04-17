package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.LoginRepository
import retrofit2.Response

// Clase que implementa la interfaz LoginRepository
// Aquí se hace la conexión con Retrofit, capa que habla con el red
//class LoginRepositoryImpl (private val apiService: ApiService): LoginRepository {

  //  override suspend fun login (request: LoginRequest): Response<LoginResponse> {
    //    return apiService.login(request)

   // }

//}

// Fake para pruebas sin backend
class FakeLoginRepository : LoginRepository {
    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return if (request.username == "test" && request.password == "1234") {
            // Simulamos login exitoso
            Response.success(
                LoginResponse(
                    token = "fake-token-123",
                    userType = "alumno", // o "empresa", depende de lo que necesites
                    message = "Login exitoso (fake)"
                )
            )

        } else {
            // Simulamos fallo de autenticación
            Response.success(
                LoginResponse(
                    token = "",
                    userType = "",
                    message = "Error: usuario o contraseña incorrectos (fake)"
                )
            )

        }
    }
}
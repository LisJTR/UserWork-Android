package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.LoginRepository
import retrofit2.Response

// Clase que implementa la interfaz LoginRepository
 //Aquí se hace la conexión con Retrofit, capa que habla con el red
class LoginRepositoryImpl (private val apiService: ApiService): LoginRepository {

    override suspend fun login (request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)

    }

}

// Fake para pruebas sin backend
//class FakeLoginRepository : LoginRepository {
//  override suspend fun login(request: LoginRequest): Response<LoginResponse> {
//      return when {
            // Login para alumno (de pruebas)
//          request.username == "test" && request.password == "1234" -> {
//              Response.success(
//                  LoginResponse(
//                      token = "fake-token-alumno",
//                      userType = "alumno",
//                      message = "Login exitoso (fake alumno)"
//                  )
//              )
//          }

            // Login para empresa fake
//   request.username == "empresafake" && request.password == "1234" -> {
////              Response.success(
//                LoginResponse(
//                      token = "fake-token-empresa",
//                      userType = "empresa",
//                      message = "Login exitoso (fake empresa)"
//                  )
//              )
//          }

            // Si no coincide nada, error
// else -> {
//              Response.success(
//                  LoginResponse(
//                      token = "",
//                      userType = "",
//                      message = "Error: usuario o contraseña incorrectos (fake)"
//                  )
//              )
////          }
//    }
//  }
//}

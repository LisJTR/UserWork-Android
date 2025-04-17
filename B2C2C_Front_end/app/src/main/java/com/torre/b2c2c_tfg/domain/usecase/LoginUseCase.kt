package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.data.model.LoginResponse
import com.torre.b2c2c_tfg.domain.repository.LoginRepository
import retrofit2.Response

// Clase que define el caso de uso del login
// Aisla las reglas del negocio del resto
class LoginUseCase(private val repository: LoginRepository) {

    suspend operator fun invoke(request: LoginRequest): Response<LoginResponse> {
        return repository.login(request)
    }
}
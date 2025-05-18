package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.CambiarPasswordRequest
import com.torre.b2c2c_tfg.domain.repository.SettingsRepository
import retrofit2.Response

class CambiarPasswordUseCase(private val repository: SettingsRepository) {
    suspend fun cambiarPassword(request: CambiarPasswordRequest): Response<Unit> {
        return repository.cambiarPassword(request)
    }

}
package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.SettingsRepository

class EliminarCuentaUseCase(private val repository: SettingsRepository) {
    suspend fun eliminarAlumno(id: Long) = repository.eliminarAlumno(id)
    suspend fun eliminarEmpresa(id: Long) = repository.eliminarEmpresa(id)
}
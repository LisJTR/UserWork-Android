package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository

class GetTitulacionesUnicasUseCase(
    private val repository: AlumnoRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getTitulacionesUnicas()
    }
}
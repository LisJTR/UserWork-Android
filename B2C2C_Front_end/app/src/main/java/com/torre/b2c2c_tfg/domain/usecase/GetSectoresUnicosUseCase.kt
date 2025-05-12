package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository

class GetSectoresUnicosUseCase(
    private val repository: EmpresaRepository) {
    suspend operator fun invoke(): List<String> {
        return repository.getSectoresUnicos()
    }
}
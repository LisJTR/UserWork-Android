package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.repository.OfertaRepository

class GetOfertaByIdUseCase(private val repository: OfertaRepository) {
    suspend operator fun invoke(id: Long): Oferta? {
        return repository.getOfertaById(id)
    }
}
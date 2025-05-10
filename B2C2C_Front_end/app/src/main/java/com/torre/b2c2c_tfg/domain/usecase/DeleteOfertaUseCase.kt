package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.OfertaRepository

class DeleteOfertaUseCase (private val repository: OfertaRepository) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.deleteOferta(id)
    }
}
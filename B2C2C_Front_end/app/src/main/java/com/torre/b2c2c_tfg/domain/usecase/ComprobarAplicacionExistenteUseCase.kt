package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.AplicacionOfertaRepository

class ComprobarAplicacionExistenteUseCase(private val repository: AplicacionOfertaRepository) {
    suspend operator fun invoke(alumnoId: Long, ofertaId: Long): Boolean {
        return repository.existeAplicacion(alumnoId, ofertaId)
    }
}

package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.AplicacionOferta
import com.torre.b2c2c_tfg.domain.repository.AplicacionOfertaRepository

class GetAplicacionesPorAlumnoUseCase(private val repository: AplicacionOfertaRepository) {
    suspend operator fun invoke(alumnoId: Long): List<AplicacionOferta> {
        return repository.getAplicacionesPorAlumnoId(alumnoId)
    }
}

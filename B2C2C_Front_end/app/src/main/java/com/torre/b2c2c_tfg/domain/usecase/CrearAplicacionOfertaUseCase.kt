package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.domain.repository.AplicacionOfertaRepository
import com.torre.b2c2c_tfg.data.model.AplicacionOferta

class CrearAplicacionOfertaUseCase(private val repository: AplicacionOfertaRepository) {
    suspend operator fun invoke(aplicacion: AplicacionOferta): Boolean {
        return repository.crearAplicacion(aplicacion)
    }
}
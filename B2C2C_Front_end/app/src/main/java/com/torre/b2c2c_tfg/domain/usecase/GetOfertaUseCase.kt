package com.torre.b2c2c_tfg.domain.usecase


import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.repository.OfertaRepository

class GetOfertasUseCase(private val repository: OfertaRepository) {
    suspend operator fun invoke(): List<Oferta> = repository.getOfertas()
}
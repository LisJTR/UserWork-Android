package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.AplicacionOferta
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.AplicacionOfertaRepository

class AplicacionOfertaRepositoryImpl(private val api: ApiService) : AplicacionOfertaRepository {
    override suspend fun crearAplicacion(aplicacion:AplicacionOferta): Boolean {
        return api.crearAplicacion(aplicacion).isSuccessful
    }

    override suspend fun getAplicacionesPorAlumnoId(alumnoId: Long): List<AplicacionOferta> {
        return api.getAplicacionesPorAlumnoId(alumnoId)
    }
}
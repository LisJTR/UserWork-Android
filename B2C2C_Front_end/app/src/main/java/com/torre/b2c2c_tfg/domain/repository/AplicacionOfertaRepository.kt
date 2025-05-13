package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.AplicacionOferta

interface AplicacionOfertaRepository {
    suspend fun crearAplicacion(aplicacion: AplicacionOferta): Boolean
    suspend fun getAplicacionesPorAlumnoId(alumnoId: Long): List<AplicacionOferta>
}
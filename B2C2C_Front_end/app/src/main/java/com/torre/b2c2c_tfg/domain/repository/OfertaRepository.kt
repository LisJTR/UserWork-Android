package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.Oferta
import retrofit2.Response

interface OfertaRepository {
    suspend fun crearOferta(oferta: Oferta): Boolean
    suspend fun getOfertas(): List<Oferta>
    suspend fun getOfertasByEmpresaId(empresaId: Long): List<Oferta>
    suspend fun deleteOferta(id: Int): Boolean
    suspend fun getOfertaById(id: Long): Oferta?

}
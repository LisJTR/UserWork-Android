package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.OfertaRepository
import kotlinx.coroutines.delay


class OfertaRepositoryImpl(
  private val api: ApiService
) : OfertaRepository {

   override suspend fun crearOferta(oferta: Oferta): Boolean {
     return api.crearOferta(oferta).isSuccessful
    }

    override suspend fun getOfertasByEmpresaId(empresaId: Long): List<Oferta> {
        return try {
            api.getOfertasByEmpresaId(empresaId)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getOfertas(): List<Oferta> {
        return try {
            api.getOfertas()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteOferta(id: Int): Boolean {
        return try {
            api.deleteOfertasDesdePerfil(id).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

}

//class FakeOfertaRepository : OfertaRepository {

//   private val listaOfertas = mutableListOf<Oferta>()

//  override suspend fun crearOferta(oferta: Oferta): Boolean {
//    delay(500) // Simula un poco de red
//   listaOfertas.add(oferta)
//   println("Oferta creada (fake): $oferta")
//   return true
//  }

//   override suspend fun getOfertas(): List<Oferta> {
//      delay(500) // Simula una llamada al backend
//      return listaOfertas.toList() // Devuelve una copia para evitar modificar la original accidentalmente
//   }


//}
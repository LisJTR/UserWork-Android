package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.OfertaRepository
import kotlinx.coroutines.delay


//lass OfertaRepositoryImpl(
//  private val api: ApiService
//) : OfertaRepository {
//   override suspend fun crearOferta(oferta: Oferta): Boolean {
//     return api.crearOferta(oferta).isSuccessful
// }
//}

class FakeOfertaRepository : OfertaRepository {

    private val listaOfertas = mutableListOf<Oferta>()

    override suspend fun crearOferta(oferta: Oferta): Boolean {
        delay(500) // Simula un poco de red
        listaOfertas.add(oferta)
        println("Oferta creada (fake): $oferta")
        return true
    }
}
package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository
import kotlinx.coroutines.delay

//class EmpresaRepositoryImpl(
    //private val apiService: ApiService
//) : EmpresaRepository {
  //  override suspend fun getEmpresa(): Empresa {
    //    return apiService.getEmpresa()
    //}

    //override suspend fun updateEmpresa(empresa: Empresa): Boolean {
      //  return apiService.updateEmpresa(empresa).isSuccessful
    //}
//}

// Clase fake para pruebas
class FakeEmpresaRepository : EmpresaRepository {

    private var empresaFake = Empresa(
        nombre = "Empresa Fake S.L.",
        username = "empresafake",
        password = "1234",
        sector = "Tecnología",
        ciudad = "Madrid",
        telefono = "912345678",
        correoElectronico = "info@empresa.fake",
        descripcion = "Somos una empresa de prueba",
        imagen = null
    )

    override suspend fun getEmpresa(): Empresa {
        delay(1000) // Simula una llamada a red
        return empresaFake
    }

    override suspend fun updateEmpresa(empresa: Empresa): Boolean {
        delay(1000) // Simula guardar
        empresaFake = empresa
        return true
    }
}

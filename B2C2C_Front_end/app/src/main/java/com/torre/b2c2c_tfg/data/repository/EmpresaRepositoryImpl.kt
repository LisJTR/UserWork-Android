package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository
import kotlinx.coroutines.delay

class EmpresaRepositoryImpl(
    private val apiService: ApiService
) : EmpresaRepository {
    override suspend fun getEmpresa(): Empresa {
        return apiService.getEmpresa()
    }

    override suspend fun updateEmpresa(empresa: Empresa): Boolean {
        return apiService.updateEmpresa(empresa).isSuccessful
    }

    override suspend fun getEmpresaById(id: Long): Empresa {
        return apiService.getEmpresaById(id)
    }

    override suspend fun createEmpresa(empresa: Empresa): Empresa? {
        val response = apiService.crearEmpresa(empresa)
        return if (response.isSuccessful) response.body() else null
    }
    override suspend fun getSectoresUnicos(): List<String> {
        return apiService.getSectoresUnicos()
    }
}

// Clase fake para pruebas
// class FakeEmpresaRepository : EmpresaRepository {

//  private var empresaFake = Empresa(
//      nombre = "Empresa Fake S.L.",
//      username = "empresafake",
//      password = "1234",
//      sector = "Tecnología",
//      ciudad = "Madrid",
//      telefono = "912345678",
//      correoElectronico = "info@empresa.fake",
//      descripcion = "Somos una empresa de prueba",
//      imagen = null
//  )

//  override suspend fun getEmpresa(): Empresa {
//      delay(1000) // Simula una llamada a red
//      return empresaFake
//  }

//  override suspend fun updateEmpresa(empresa: Empresa): Boolean {
//      delay(1000) // Simula guardar
//      empresaFake = empresa
//      return true
//  }

//  override suspend fun getAllEmpresas(): List<Empresa> {
//      return listOf(
//          Empresa(
//              nombre = "TechSoft",
//              username = "techsoft123",
//              password = "1234",
//              sector = "Tecnología",
//              ciudad = "Madrid",
//              telefono = "912345678",
//              correoElectronico = "tech@soft.com",
//              descripcion = "Empresa de software",
//              imagen = null
//          ),
//          Empresa(
//              nombre = "EduWorld",
//              username = "eduworld",
//              password = "abcd",
//              sector = "Educación",
//              ciudad = "Barcelona",
//              telefono = "934567891",
//              correoElectronico = "info@eduworld.com",
//              descripcion = "Centro de formación",
//              imagen = null
//          ),
//          Empresa(
//              nombre = "FinCorp",
//              username = "fincorp",
//              password = "pass123",
//              sector = "Finanzas",
//              ciudad = "Valencia",
//              telefono = "987654321",
//              correoElectronico = "contacto@fincorp.com",
//              descripcion = "Consultora financiera",
//              imagen = null
//          )
//      )
//  }
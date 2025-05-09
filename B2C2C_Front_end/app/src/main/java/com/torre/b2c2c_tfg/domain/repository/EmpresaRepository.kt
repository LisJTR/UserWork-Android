package com.torre.b2c2c_tfg.domain.repository

import com.torre.b2c2c_tfg.data.model.Empresa

interface EmpresaRepository {
    suspend fun getEmpresa(): Empresa
    suspend fun getEmpresaById(id: Long): Empresa
    suspend fun updateEmpresa(empresa: Empresa): Boolean
    suspend fun createEmpresa(empresa: Empresa): Empresa?
}

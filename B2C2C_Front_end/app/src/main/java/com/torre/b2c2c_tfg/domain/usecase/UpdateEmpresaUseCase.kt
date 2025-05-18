package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository

class UpdateEmpresaUseCase(private val repository: EmpresaRepository) {

    suspend operator fun invoke(empresa: Empresa): Empresa {
        repository.updateEmpresa(empresa) // ← sigue ejecutando el update
        return empresa // ← devuelve la misma empresa
    }
}
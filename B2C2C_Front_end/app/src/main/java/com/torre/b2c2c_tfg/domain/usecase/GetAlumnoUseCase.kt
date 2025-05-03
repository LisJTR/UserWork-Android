package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository


class GetAlumnoUseCase(private val repository: AlumnoRepository) {

    suspend operator fun invoke(): Alumno = repository.getAlumno()
}


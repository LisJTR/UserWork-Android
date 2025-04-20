package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.repository.FakeAlumnoRepository


class GetAlumnoUseCase(private val repository: FakeAlumnoRepository) {

    suspend operator fun invoke(): Alumno = repository.getAlumno()
}
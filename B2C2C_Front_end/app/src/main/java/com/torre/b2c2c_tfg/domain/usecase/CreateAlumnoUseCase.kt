package com.torre.b2c2c_tfg.domain.usecase

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository

class CreateAlumnoUseCase(private val repository: AlumnoRepository) {
    suspend operator fun invoke(alumno: Alumno): Alumno = repository.createAlumno(alumno)
}

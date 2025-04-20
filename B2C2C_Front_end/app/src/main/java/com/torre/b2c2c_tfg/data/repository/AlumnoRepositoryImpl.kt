package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository
import kotlinx.coroutines.delay

//class AlumnoRepositoryImpl(
//private val apiService: ApiService
//) : AlumnoRepository {
//  override suspend fun getAlumno(): Empresa {
//    return apiService.getAlumno()
//}

//override suspend fun updateAlumno(alumno: Alumno): Boolean {
//  return apiService.updateAlumno(alumno).isSuccessful
//}
//}

// Clase fake para pruebas
class FakeAlumnoRepository : AlumnoRepository {

    private var alumnoFake = Alumno(
        nombre = "Juan",
        apellido= "Torres",
        username = "juanT",
        password = "1234",
        telefono = "123456789",
        correoElectronico ="ejemploJ@correo.com",
        ciudad = "Madrid",
        direccion = "Calle falsa 123",
        centro = "IES Carlos III",
        titulacion = "Ingeniero",
        descripcion = "Hola",
        habilidades = "Java, Kotlin, Android",
        imagen = null,
        cvUri = null,
        docUri = null
    )


    override suspend fun getAlumno(): Alumno {
        delay(1000) // Simula una llamada a red
        return alumnoFake
    }

    override suspend fun updateAlumno(alumno: Alumno): Boolean {
        delay(1000) // Simula guardar
        alumnoFake = alumno
        return true
    }
}

package com.torre.b2c2c_tfg.data.repository

import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.remote.ApiService
import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository
import kotlinx.coroutines.delay

class AlumnoRepositoryImpl(
    private val apiService: ApiService
        ) : AlumnoRepository {
          override suspend fun getAlumno(): Alumno {
            return apiService.getAlumno()
        }

        override suspend fun updateAlumno(alumno: Alumno): Boolean {
          return apiService.updateAlumno(alumno).isSuccessful
        }
}

// Clase fake para pruebas
// class FakeAlumnoRepository : AlumnoRepository {

//  private var alumnoFake = Alumno(
//      nombre = "Juan",
//      apellido= "Torres",
//      username = "juanT",
//      password = "1234",
//      telefono = "123456789",
//      correoElectronico ="ejemploJ@correo.com",
//      ciudad = "Madrid",
//      direccion = "Calle falsa 123",
//      centro = "IES Carlos III",
//      titulacion = "Ingeniero",
//      descripcion = "Hola",
//      habilidades = "Java, Kotlin, Android",
//      imagen = null,
//      cvUri = null,
//      docUri = null
//  )


//  override suspend fun getAlumno(): Alumno {
//      delay(1000) // Simula una llamada a red
//      return alumnoFake
//  }
//
//  override suspend fun updateAlumno(alumno: Alumno): Boolean {
//      delay(1000) // Simula guardar
//      alumnoFake = alumno
//      return true
//  }

//  override suspend fun getAllAlumnos(): List<Alumno> {
//      return listOf(
//          Alumno(
//              nombre = "Ana",
//              apellido = "García",
//              username = "ana123",
// //              password = "1234",
//           telefono = "123456789",
//              correoElectronico = "ana@correo.com",
//              ciudad = "Madrid",
//              direccion = "Calle 1",
//              centro = "IES Central",
//              titulacion = "Informática",
//              descripcion = "Soy Ana y estudio informática.",
//              habilidades = "Java, Kotlin",
//              imagen = null,
//              cvUri = null,
//              docUri = null
//          ),
//          Alumno(
//              nombre = "Luis",
//              apellido = "Martínez",
// //              username = "luis321",
//           password = "5678",
//              telefono = "987654321",
//              correoElectronico = "luis@correo.com",
//              ciudad = "Barcelona",
//              direccion = "Calle 2",
//              centro = "IES Norte",
//              titulacion = "Marketing",
//              descripcion = "Soy Luis y me encanta el marketing digital.",
//              habilidades = "SEO, Redes Sociales",
//              imagen = null,
//              cvUri = null,
//              docUri = null
//          ),
//          Alumno(
//              nombre = "Laura",
//              apellido = "Fernández",
//              username = "laura456",
//              password = "abcd",
//              telefono = "555444333",
//              correoElectronico = "laura@correo.com",
//              ciudad = "Valencia",
//              direccion = "Calle 3",
//              centro = "IES Este",
//              titulacion = "Administración",
//              descripcion = "Apasionada por la gestión empresarial.",
//              habilidades = "Excel, Contabilidad",
//              imagen = null,
//              cvUri = null,
//              docUri = null
//          )
//      )
//  }


// }
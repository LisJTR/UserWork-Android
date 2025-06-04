package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.repository.AlumnoRepository
import com.torre.b2c2c_tfg.domain.repository.EmpresaRepository
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.domain.repository.OfertaRepository
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import kotlinx.coroutines.flow.combine

class OfertasScreenViewModel (
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val getEmpresaUseCase: GetEmpresaUseCase,
    private val getSectoresUnicosUseCase: GetSectoresUnicosUseCase,
    private val getTitulacionesUnicasUseCase: GetTitulacionesUnicasUseCase,
    private val alumnoRepository: AlumnoRepository,
    private val empresaRepository: EmpresaRepository,
    private val getTodasLasOfertasUseCase: GetTodasLasOfertasUseCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa
    val sectoresUnicos = MutableStateFlow<List<String>>(emptyList())
    val titulacionesUnicas = MutableStateFlow<List<String>>(emptyList())
    val empresas = MutableStateFlow<List<Empresa>>(emptyList())
    val alumnos = MutableStateFlow<List<Alumno>>(emptyList())
    val ofertas = MutableStateFlow<List<Oferta>>(emptyList())
    val filtroSeleccionado = MutableStateFlow<String?>(null)

    fun iniciarAutoRefresco(userType: String, userId: Long, intervaloMs: Long = 5000L) {
        viewModelScope.launch {
            kotlinx.coroutines.flow.flow {
                while (true) {
                    emit(Unit)
                    kotlinx.coroutines.delay(intervaloMs)
                }
            }.collect {
                if (userType == "alumno") {
                    cargarAlumno(userId)
                    cargarFiltros("alumno")
                } else if (userType == "empresa") {
                    cargarEmpresa(userId)
                    cargarFiltros("empresa")
                }
                cargarTodasLasOfertas()
            }
        }
    }


    fun cargarAlumno(id: Long) {
        viewModelScope.launch {
            _alumno.value = getAlumnoUseCase(id)
        }
    }

    fun cargarEmpresa(id: Long) {
        viewModelScope.launch {
            _empresa.value = getEmpresaUseCase(id)
        }
    }

    fun cargarFiltros(userType: String) {
        viewModelScope.launch {
            try {
                if (userType == "alumno") {
                    val sectores = getSectoresUnicosUseCase()
                    println("Sectores únicos cargados: $sectores")
                    sectoresUnicos.value = sectores
                } else if (userType == "empresa") {
                    val titulaciones = getTitulacionesUnicasUseCase()
                    println("Titulaciones únicas cargadas: $titulaciones")
                    titulacionesUnicas.value = titulaciones
                }
            } catch (e: Exception) {
                println("ERROR AL CARGAR FILTROS: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun cargarEmpresas() {
        viewModelScope.launch {
            try {
                val lista = empresaRepository.getAllEmpresas()
                empresas.value = empresaRepository.getAllEmpresas()
                lista.forEach { println(" Empresa: ${it.id} - ${it.nombre}") }
            } catch (e: Exception) {
                println("Error cargando empresas: ${e.message}")
            }
        }
    }

    fun cargarAlumnos() {
        viewModelScope.launch {
            try {
                alumnos.value = alumnoRepository.getAllAlumnos()
            } catch (e: Exception) {
                println("Error cargando alumnos: ${e.message}")
            }
        }
    }

    fun cargarTodasLasOfertas() {
        viewModelScope.launch {
            try {
                val lista = getTodasLasOfertasUseCase()
                ofertas.value = lista
                lista.forEach {
                    println(" Oferta: ${it.titulo} - Empresa ID: ${it.empresaId}")
                }
            } catch (e: Exception) {
                println("Error al cargar ofertas: ${e.message}")
            }
        }
    }

    val ofertasFiltradas = ofertas.combine(filtroSeleccionado) { listaOfertas, filtro ->
        if (filtro.isNullOrBlank()) {
            listaOfertas
        } else {
            // Solo incluye ofertas cuya empresa coincida con el filtro
            listaOfertas.filter { oferta ->
                val empresa = empresas.value.find { it.id?.toLong() == oferta.empresaId.toLong() }
                empresa?.sector == filtro
            }
        }
    }

    val alumnosFiltrados = alumnos.combine(filtroSeleccionado) { listaAlumnos, filtro ->
        if (filtro.isNullOrBlank()) {
            listaAlumnos
        } else {
            listaAlumnos.filter { it.titulacion == filtro }
        }
    }
}






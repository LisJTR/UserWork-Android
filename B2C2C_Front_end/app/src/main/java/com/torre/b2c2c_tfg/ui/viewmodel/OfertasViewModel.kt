package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfertasViewModel (
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val getEmpresaUseCase: GetEmpresaUseCase,
    private val getSectoresUnicosUseCase: GetSectoresUnicosUseCase,
    private val getTitulacionesUnicasUseCase: GetTitulacionesUnicasUseCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa

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

    val sectoresUnicos = MutableStateFlow<List<String>>(emptyList())
    val titulacionesUnicas = MutableStateFlow<List<String>>(emptyList())

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
}

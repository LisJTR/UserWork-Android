package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.GetAplicacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MisOfertasScreenViewModel(
    private val getAplicacionesUseCase: GetAplicacionesPorAlumnoUseCase,
    private val getTodasLasOfertasUseCase: GetTodasLasOfertasUseCase
) : ViewModel() {

    private val _ofertasAplicadas = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasAplicadas: StateFlow<List<Oferta>> = _ofertasAplicadas

    fun cargarOfertasAplicadas(alumnoId: Long) {
        viewModelScope.launch {
            try {
                val aplicaciones = getAplicacionesUseCase(alumnoId)
                println("📥 Aplicaciones obtenidas del alumno $alumnoId: ${aplicaciones.map { it.ofertaId }}")

                val todasLasOfertas = getTodasLasOfertasUseCase()
                println("📦 Todas las ofertas disponibles: ${todasLasOfertas.map { it.id }}")

                val idsAplicadas = aplicaciones.map { it.ofertaId }
                val ofertasFiltradas = todasLasOfertas.filter { oferta ->
                    oferta.id?.toLong() in idsAplicadas
                }

                println("✅ Ofertas aplicadas que se van a mostrar: ${ofertasFiltradas.map { it.id }}")

                _ofertasAplicadas.value = ofertasFiltradas
            } catch (e: Exception) {
                println("❌ Error al cargar ofertas aplicadas: ${e.message}")
            }
        }
    }
}

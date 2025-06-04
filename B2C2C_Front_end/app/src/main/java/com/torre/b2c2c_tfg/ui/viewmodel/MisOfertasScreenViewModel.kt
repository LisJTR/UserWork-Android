package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Invitacion
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.GetAplicacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MisOfertasScreenViewModel(
    private val getAplicacionesUseCase: GetAplicacionesPorAlumnoUseCase,
    private val getTodasLasOfertasUseCase: GetTodasLasOfertasUseCase,
    private val getInvitacionesPorEmpresaUseCase: GetInvitacionPorEmpresaUseCase
) : ViewModel() {

    private val _ofertasAplicadas = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasAplicadas: StateFlow<List<Oferta>> = _ofertasAplicadas
    private val _invitacionesRecibidas = MutableStateFlow<List<Invitacion>>(emptyList())
    val invitacionesRecibidas: StateFlow<List<Invitacion>> = _invitacionesRecibidas

    fun iniciarAutoRefresco(alumnoId: Long?, empresaId: Long?, intervaloMs: Long = 5000L) {
        viewModelScope.launch {
            kotlinx.coroutines.flow.flow {
                while (true) {
                    emit(Unit)
                    kotlinx.coroutines.delay(intervaloMs)
                }
            }.collect {
                alumnoId?.let { cargarOfertasAplicadas(it) }
                empresaId?.let { cargarInvitaciones(it) }
            }
        }
    }

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


    fun cargarInvitaciones(empresaId: Long) {
        viewModelScope.launch {
            try {
                _invitacionesRecibidas.value = getInvitacionesPorEmpresaUseCase(empresaId)
            } catch (e: Exception) {
                println("❌ Error al cargar invitaciones: ${e.message}")
            }
        }
    }

}

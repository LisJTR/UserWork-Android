package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.CrearAplicacionOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetOfertaByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.data.model.AplicacionOferta


class OfertaDetalleScreenViewModel(
    private val getOfertaByIdUseCase: GetOfertaByIdUseCase,
    private val getEmpresaUseCase: GetEmpresaUseCase,
    private val crearAplicacionOfertaUseCase: CrearAplicacionOfertaUseCase
) : ViewModel() {

    private val _oferta = MutableStateFlow<Oferta?>(null)
    val oferta: StateFlow<Oferta?> = _oferta

    private val _empresa = MutableStateFlow<Empresa?>(null)
    val empresa: StateFlow<Empresa?> = _empresa

    private val _aplicacionExitosa = MutableStateFlow<Boolean?>(null)
    val aplicacionExitosa: StateFlow<Boolean?> = _aplicacionExitosa

    fun cargarOfertaConEmpresa(idOferta: Long) {
        viewModelScope.launch {
            try {
                val ofertaData = getOfertaByIdUseCase(idOferta)
                _oferta.value = ofertaData

                val empresaId = ofertaData?.empresaId?.toLong()
                if (empresaId != null) {
                    _empresa.value = getEmpresaUseCase(empresaId)
                }

            } catch (e: Exception) {
                println("Error al cargar datos de oferta: ${e.message}")
            }
        }
    }

    fun aplicarAOferta(alumnoId: Long, ofertaId: Long) {
        viewModelScope.launch {
            try {
                println("🛠️ Enviando aplicación con ofertaId = $ofertaId y alumnoId = $alumnoId")
                val aplicacion = AplicacionOferta(
                    alumnoId = alumnoId,
                    ofertaId = ofertaId,
                    fechaAplicacion = null, // puedes usar una fecha actual aquí si lo deseas
                    estado = "pendiente"
                )
                val exito = crearAplicacionOfertaUseCase(aplicacion)
                _aplicacionExitosa.value = exito
            } catch (e: Exception) {
                _aplicacionExitosa.value = false
            }
        }
    }
}
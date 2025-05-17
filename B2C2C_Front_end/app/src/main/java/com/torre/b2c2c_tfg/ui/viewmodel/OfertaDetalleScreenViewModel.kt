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
import com.torre.b2c2c_tfg.data.model.Notificacion
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.ComprobarAplicacionExistenteUseCase
import com.torre.b2c2c_tfg.domain.usecase.CrearNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEstadoRespuestaPorIdUseCase

class OfertaDetalleScreenViewModel(
private val getOfertaByIdUseCase: GetOfertaByIdUseCase,
private val getEmpresaUseCase: GetEmpresaUseCase,
private val crearAplicacionOfertaUseCase: CrearAplicacionOfertaUseCase,
private val crearNotificacionUseCase: CrearNotificacionUseCase,
private val comprobarAplicacionExistenteUseCase: ComprobarAplicacionExistenteUseCase,
private val actualizarNotificacionUseCase: ActualizarNotificacionUseCase
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
                    fechaAplicacion = null,
                    estado = "pendiente"
                )
                val exito = crearAplicacionOfertaUseCase(aplicacion)
                _aplicacionExitosa.value = exito

                if (exito) {
                    // CREAR NOTIFICACIÓN PARA LA EMPRESA
                    val result = crearNotificacionUseCase(
                        Notificacion(
                            tipo = "aplicacion",
                            mensaje = "Un alumno ha aplicado a tu oferta.",
                            alumnoId = alumnoId,
                            ofertaId = ofertaId,
                            empresaId = empresa.value?.id?.toLong(), // usa la empresa cargada
                            destinatarioTipo = "empresa"
                        )
                    )
                    println("✅ ¿Se creó la notificación? $result")
                }

            } catch (e: Exception) {
                _aplicacionExitosa.value = false
            }
        }
    }

    private val _yaAplicada = MutableStateFlow(false)
    val yaAplicada: StateFlow<Boolean> = _yaAplicada

    fun comprobarSiYaAplicada(alumnoId: Long, ofertaId: Long) {
        viewModelScope.launch {
            _yaAplicada.value = comprobarAplicacionExistenteUseCase(alumnoId, ofertaId) // ✅ BIEN USADO
        }
    }

    fun responderNotificacion(idNotificacion: Long, estadoRespuesta: String) {
        viewModelScope.launch {
            val exito = actualizarNotificacionUseCase(idNotificacion, estadoRespuesta)
            if (!exito) {
                println("❌ Error al actualizar estado de la notificación")
            } else {
                println("✅ Notificación actualizada correctamente")
            }
        }
    }

}
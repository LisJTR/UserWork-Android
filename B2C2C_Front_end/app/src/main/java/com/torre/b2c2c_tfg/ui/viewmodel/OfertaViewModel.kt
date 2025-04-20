package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.CrearOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfertaViewModel(
    private val crearOfertaUseCase: CrearOfertaUseCase,
    private val getOfertasUseCase: GetOfertasUseCase
) : ViewModel() {

    private val _ofertas = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertas: StateFlow<List<Oferta>> = _ofertas

    fun guardarOferta(oferta: Oferta) {
        viewModelScope.launch {
            val resultado = crearOfertaUseCase(oferta)
            println("Resultado guardar oferta: $resultado")
            // Puedes actualizar la lista si quieres aquí también
        }
    }

    fun cargarOfertas() {
        viewModelScope.launch {
            try {
                val lista = getOfertasUseCase()
                _ofertas.value = lista
            } catch (e: Exception) {
                println("Error al cargar ofertas: ${e.message}")
            }
        }
    }
}

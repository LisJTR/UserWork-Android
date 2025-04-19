package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.CrearOfertaUseCase
import kotlinx.coroutines.launch

class OfertaViewModel(
    private val crearOfertaUseCase: CrearOfertaUseCase
) : ViewModel() {

    fun guardarOferta(oferta: Oferta) {
        viewModelScope.launch {
            val resultado = crearOfertaUseCase(oferta)
            println("Resultado guardar oferta: $resultado")
            // Aquí podrías mostrar un mensaje de éxito o error
        }
    }
}
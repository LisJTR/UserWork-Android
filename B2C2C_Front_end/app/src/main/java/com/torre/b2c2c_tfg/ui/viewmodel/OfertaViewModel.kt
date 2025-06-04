package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.domain.usecase.CrearOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.DeleteOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfertaViewModel(
    private val crearOfertaUseCase: CrearOfertaUseCase,
    private val getOfertasUseCase: GetOfertasUseCase,
    private val deleteOfertaUseCase: DeleteOfertaUseCase
) : ViewModel() {

    private val _ofertas = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertas: StateFlow<List<Oferta>> = _ofertas

    fun guardarOferta(oferta: Oferta, onResult: (Oferta?) -> Unit = {}) {
        viewModelScope.launch {
            try {
                println("🛠️ Enviando oferta: $oferta")
                val resultado = crearOfertaUseCase(oferta)
                println(" Resultado guardar oferta: $resultado")
                onResult(if (resultado) oferta else null)
            } catch (e: Exception) {
                println(" Error guardando oferta: ${e.message}")
                onResult(null)
            }
        }
    }

    fun cargarOfertas(empresaId: Long) {
        viewModelScope.launch {
            try {
                val lista = getOfertasUseCase(empresaId)
                _ofertas.value = lista
            } catch (e: Exception) {
                println("Error al cargar ofertas: ${e.message}")
            }
        }
    }

    fun eliminarOferta(id: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val ok = deleteOfertaUseCase(id)
            if (ok) {
                _ofertas.value = _ofertas.value.filterNot { it.id == id }
                onSuccess()
            } else {
                println("Error al eliminar oferta con ID: $id")
            }
        }
    }

}

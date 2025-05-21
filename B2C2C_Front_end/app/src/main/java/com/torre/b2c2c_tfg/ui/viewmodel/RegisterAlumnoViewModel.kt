package com.torre.b2c2c_tfg.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.os.FileUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateAlumnoUserCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.domain.usecase.CreateAlumnoUseCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody

class RegisterAlumnoViewModel(
    private val getAlumnoUseCase: GetAlumnoUseCase,
    private val updateAlumnoUserCase: UpdateAlumnoUserCase,
    private val createAlumnoUseCase: CreateAlumnoUseCase
) : ViewModel() {

    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno

    var alumnoId: Long? = null
        private set

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    fun cargarDatos(id: Long) {
        viewModelScope.launch {
            try {
            _alumno.value = getAlumnoUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun guardarDatos(alumno: Alumno, esEdicion: Boolean) {
        viewModelScope.launch {
            try {
                val resultado = if (esEdicion) {
                    updateAlumnoUserCase(alumno)
                } else {
                    createAlumnoUseCase(alumno)
                }

                _alumno.value = resultado
                alumnoId = resultado.id?.toLong()

                // Limpia el mensaje de error si se guardó bien
                _mensajeError.value = ""
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 409) {
                    _mensajeError.value = "El usuario o correo electrónico ya están en uso"
                } else {
                    _mensajeError.value = "Error del servidor: ${e.code()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error inesperado: ${e.message}"
            }
        }
    }

}
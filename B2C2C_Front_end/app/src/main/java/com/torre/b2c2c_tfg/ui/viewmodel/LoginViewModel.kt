package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.b2c2c_tfg.data.model.LoginRequest
import com.torre.b2c2c_tfg.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Clase que conecta la UI con el dominio
// Esta la logica de la UI que necesita para funcionar (llamar al login, guardar resultado, etc)
class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginResult = MutableStateFlow<String>("")
    // Se usa StateFlow para que la UI pueda observar los cambios en el resultado del login
    val loginResult: StateFlow<String> = _loginResult

    fun login(username: String, email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase(LoginRequest(username, email, password))

            if (result.isSuccessful && result.body() != null) {
                _loginResult.value = "Login exitoso: ${result.body()?.token}"
            } else {
                _loginResult.value = "Error de login"
            }
        }
    }
}
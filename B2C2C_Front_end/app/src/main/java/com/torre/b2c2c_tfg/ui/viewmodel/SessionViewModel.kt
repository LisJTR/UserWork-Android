package com.torre.b2c2c_tfg.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.torre.b2c2c_tfg.ui.util.SessionManager

class SessionViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId

    private val _userType = MutableStateFlow<String?>(null)
    val userType: StateFlow<String?> = _userType

    fun setSession(id: Long, type: String) {
        _userId.value = id
        _userType.value = type

        viewModelScope.launch {
            sessionManager.saveSession(id, type)
        }
    }

    fun clearSession() {
        _userId.value = null
        _userType.value = null

        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }

    fun loadSession() {
        viewModelScope.launch {
            val (savedId, savedType) = sessionManager.getSession()
            _userId.value = savedId
            _userType.value = savedType
        }
    }
}

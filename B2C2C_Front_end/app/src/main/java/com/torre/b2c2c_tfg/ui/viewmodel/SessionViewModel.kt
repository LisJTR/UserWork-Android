package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel : ViewModel() {

    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId

    private val _userType = MutableStateFlow<String?>(null)
    val userType: StateFlow<String?> = _userType

    fun setSession(id: Long, type: String) {
        _userId.value = id
        _userType.value = type
    }

    fun clearSession() {
        _userId.value = null
        _userType.value = null
    }
}
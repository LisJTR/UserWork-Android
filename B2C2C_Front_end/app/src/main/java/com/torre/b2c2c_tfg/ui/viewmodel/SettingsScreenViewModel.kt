package com.torre.b2c2c_tfg.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsScreenViewModel(
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _navigateToWelcome = MutableStateFlow(false)
    val navigateToWelcome: StateFlow<Boolean> = _navigateToWelcome

    fun logout() {
        sessionViewModel.clearSession()
        _navigateToWelcome.value = true
    }

    fun resetNavigationFlag() {
        _navigateToWelcome.value = false
    }
}
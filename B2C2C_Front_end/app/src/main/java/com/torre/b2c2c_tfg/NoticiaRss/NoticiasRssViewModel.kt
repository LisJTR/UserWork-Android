package com.torre.b2c2c_tfg.NoticiaRss

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoticiasRssViewModel : ViewModel() {
    var noticias by mutableStateOf<List<NoticiaRss>>(emptyList())
        private set

    fun cargarNoticias(url: String) {
        viewModelScope.launch {
            noticias = leerRssDesdeUrl(url)
        }
    }
}
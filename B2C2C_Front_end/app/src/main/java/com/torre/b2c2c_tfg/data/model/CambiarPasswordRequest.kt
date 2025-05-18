package com.torre.b2c2c_tfg.data.model

data class CambiarPasswordRequest(
    val id: Long,
    val tipo: String,
    val passwordActual: String,
    val passwordNueva: String
)

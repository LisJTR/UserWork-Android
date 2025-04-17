package com.torre.b2c2c_tfg.data.model

// Es el modelo de datos que se manda al servidor para hacer el login (datos del usuario)
// Retrofit necesita una clase para convertir los datos en JSON
data class LoginRequest(
    val username: String,
    val email: String,
    val password: String
)
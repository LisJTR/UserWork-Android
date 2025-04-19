package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

// Es el modelo de datos que se manda al servidor para hacer el login (datos del usuario)
// Retrofit necesita una clase para convertir los datos en JSON
data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("correo_electronico")
    val email: String,
    @SerializedName("password")
    val password: String
)
package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

// Es la respuesta que nos da el servidor al hacer el login
// Retrofit lo convierte en un objeto de esta clase
data class LoginResponse(
    @SerializedName
        ("mensaje") val mensaje: String,
    @SerializedName
        ("id") val id: Long?,
    @SerializedName
        ("tipo") val tipo: String?
)


package com.torre.b2c2c_tfg.data.model

// Es la respuesta que nos da el servidor al hacer el login
// Retrofit lo convierte en un objeto de esta clase
data class LoginResponse(
        val token: String,
        val userType: String,
        val message: String

 )

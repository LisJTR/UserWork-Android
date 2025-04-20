package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class Empresa (
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("sector")
    val sector: String,
    @SerializedName("ciudad")
    val ciudad: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("logo")
    val imagen: String? = null


)
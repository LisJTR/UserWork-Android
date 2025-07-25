package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class Alumno(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("apellido")
    val apellido: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("correoElectronico")
    val correoElectronico: String? = null,
    @SerializedName("ciudad")
    val ciudad: String,
    @SerializedName("direccion")
    val direccion: String,
    @SerializedName("centro")
    val centro: String,
    @SerializedName("titulacion")
    val titulacion: String,
    @SerializedName("descripcion") // Cambia el nombre a "conocimientos" a descripcion o eliminar el campo y añadir descripcion en la BBDD
    val descripcion: String,
    @SerializedName("aptitudes")
    val habilidades: String,
    @SerializedName("foto")
    val imagen: String? = null,
    @SerializedName("curriculum")
    val cvUri: String? = null,
    @SerializedName("nombreDoc")
    val nombreDoc: String? = null
)

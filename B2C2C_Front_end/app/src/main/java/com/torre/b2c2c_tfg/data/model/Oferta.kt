package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class Oferta(
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("aptitudes")
    val aptitudes: String,
    @SerializedName("que_se_ofrece")
    val queSeOfrece: String,
    @SerializedName("empresa_Id")
    val empresaId: Int, // variable para identificar a qué empresa pertenece la oferta
    @SerializedName("publicada")
    val publicada: Boolean = true,
    @SerializedName("fecha_publicacion")
    val fechaPublicacion: String? = null
)

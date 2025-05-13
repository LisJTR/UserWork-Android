package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class AplicacionOferta(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("alumno_id")
    val alumnoId: Long,
    @SerializedName("oferta_id")
    val ofertaId: Long,
    @SerializedName("fecha_aplicacion")
    val fechaAplicacion: String?,
    @SerializedName("estado")
    val estado: String
)
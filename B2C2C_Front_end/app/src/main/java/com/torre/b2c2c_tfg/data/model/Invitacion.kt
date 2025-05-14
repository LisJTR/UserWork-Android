package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class Invitacion(

    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("empresa_id")
    val empresaId: Long,
    @SerializedName("alumno_id")
    val alumnoId: Long,
    @SerializedName("oferta_id")
    val ofertaId: Long,
    @SerializedName("fecha")
    val fecha: String? = null,
    val estado: String = "pendiente"
)

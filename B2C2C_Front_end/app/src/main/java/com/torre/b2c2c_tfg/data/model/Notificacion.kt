package com.torre.b2c2c_tfg.data.model

import com.google.gson.annotations.SerializedName

data class Notificacion(

    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("tipo")
    val tipo: String,
    @SerializedName("mensaje")
    val mensaje: String,
    @SerializedName("alumno_id")
    val alumnoId: Long? = null,
    @SerializedName("empresa_id")
    val empresaId: Long? = null,
    @SerializedName("oferta_id")
    val ofertaId: Long? = null,
    @SerializedName("destinatario_tipo")
    val destinatarioTipo: String,
    @SerializedName("leido")
    val leido: Boolean = false,
    @SerializedName("estado_respuesta")
    val estadoRespuesta: String? = null,
    @SerializedName("fecha")
    val fecha: String? = null
)
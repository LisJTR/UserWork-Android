package com.torre.b2c2c_tfg.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileUploadApi {
    @Multipart
    @POST("api/files/upload")
    suspend fun uploadFile(
        // Forma estandar de enviar un archivo binarios
        @Part file: MultipartBody.Part,
        @Part("oldFile") oldFile: RequestBody? = null
    ): Response<ResponseBody>
}

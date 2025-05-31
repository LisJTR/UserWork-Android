package com.torre.b2c2c_tfg.data.remote

import android.content.Context
import android.net.Uri
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


// Clase que crea una instancia de Retrofit, se configura para saber a que servidor llamar
// Centralizamos la configuracion, para usarla en toda la app
object RetrofitInstance {

    // URLs del servidor
    // url apunto al emulador mismo
    private const val BASE_URL ="http://172.19.176.1:8080/"
    // url para acceder desde el ordenador con el emulador
   // private const val BASE_URL_EMULADOR = "http://10.0.2.2:8080/"
    //val BASE_URL = BASE_URL_EMULADOR
    // url para acceder desde un dispositivo físico
    // private const val BASE_URL_LOCALHOST_REAL = "http://192.168.1.38:1234/"

    fun getInstance(context: Context): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getFileUploadApi(context: Context): FileUploadApi {
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FileUploadApi::class.java)
    }

    // URL completa en forma de texto, archivos
    fun buildFullUrl(relativePath: String?): String? {
        return relativePath?.let { BASE_URL + it.removePrefix("/") }
    }

    // Devuelve una URI, imagenes desde AsyncImage
    fun buildUri(relativePath: String?): Uri? {
        return relativePath?.let { Uri.parse(BASE_URL + it.removePrefix("/")) }
    }


}


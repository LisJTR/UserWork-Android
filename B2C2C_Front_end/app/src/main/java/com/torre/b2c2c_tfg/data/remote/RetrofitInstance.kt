package com.torre.b2c2c_tfg.data.remote

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

// Clase que crea una instancia de Retrofit, se configura para saber a que servidor llamar
// Centralizamos la configuracion, para usarla en toda la app
object RetrofitInstance {

    // URLs del servidor
    // url apunto al emulador mismo
    //private const val BASE_URL = "http://127.0.0.1:1234/"
    // url para acceder desde el ordenador con el emulador
    private const val BASE_URL_EMULADOR = "https://10.0.2.2:8443/"
    // url para acceder desde un dispositivo físico
    // private const val BASE_URL_LOCALHOST_REAL = "http://192.168.1.38:1234/"


    //val api: ApiService by lazy {
        //   Retrofit.Builder()
        //     .baseUrl(BASE_URL_EMULADOR)
        //     .addConverterFactory(GsonConverterFactory.create())
        //    .build()
    //    .create(ApiService::class.java)
    //}

    fun getInstance(context: Context): ApiService {
        val client = SecureHttpClient.getClient(context)

        return Retrofit.Builder()
            .baseUrl(BASE_URL_EMULADOR)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}


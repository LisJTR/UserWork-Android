package com.torre.b2c2c_tfg.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Clase que crea una instancia de Retrofit, se configura para saber a que servidor llamar
// Centralizamos la configuracion, para usarla en toda la app
object RetrofitInstance {

    private const val BASE_URL = "http://127.0.0.1:1234/" // URL del servidor


    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


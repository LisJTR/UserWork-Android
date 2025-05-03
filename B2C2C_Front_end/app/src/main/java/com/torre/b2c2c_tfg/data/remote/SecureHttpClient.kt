package com.torre.b2c2c_tfg.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import com.torre.b2c2c_tfg.R



object SecureHttpClient {

    fun getClient(context: Context): OkHttpClient {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val inputStream: InputStream = context.resources.openRawResource(R.raw.springboot)
        val certificate = certificateFactory.generateCertificate(inputStream) as X509Certificate
        inputStream.close()

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("springboot", certificate)

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(keyStore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.trustManagers, null)

        val trustManager = tmf.trustManagers[0] as X509TrustManager

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { hostname, _ ->
                // forzamos a aceptar cualquier hostname
                hostname == "10.0.2.2" || hostname == "localhost"
            }
            .build()
    }
}
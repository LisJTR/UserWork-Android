package com.torre.b2c2c_tfg.ui.util


import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {

    fun crearMultipartDesdeContentUri(context: Context, uri: Uri, fileName: String): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IOException("No se pudo abrir el InputStream del URI: $uri")

        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        val requestFile = tempFile.asRequestBody("application/pdf".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", fileName, requestFile)
    }

    fun copyUriToTempFile(context: Context, uri: Uri): File? {
        return try {
            val fileName = getFileNameFromUri(context, uri) ?: "documento.pdf"
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, fileName)

            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var result: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = it.getString(index)
                }
            }
        }

        if (result == null) {
            uri.path?.let {
                val cut = it.lastIndexOf('/')
                if (cut != -1) {
                    result = it.substring(cut + 1)
                }
            }
        }

        return result
    }

}

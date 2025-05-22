package com.torre.b2c2c_tfg.ui.util


import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    fun crearMultipartDesdeContentUri(
        context: Context,
        uri: Uri,
        fileName: String,
        mimeType: String
    ): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IOException("No se pudo abrir el InputStream del URI: $uri")

        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        val requestFile = tempFile.asRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", fileName, requestFile)
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                return cursor.getString(nameIndex)
            }
        }
        return uri.lastPathSegment?.substringAfterLast("/")
    }

    fun copyUriToTempFile(context: Context, uri: Uri): File? {
        return try {
            val name = getFileNameFromUri(context, uri) ?: "archivo.tmp"
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, name)

            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output -> input.copyTo(output) }
            }

            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun subirArchivoConOldFile(
        context: Context,
        archivo: File,
        nombreArchivo: String,
        mimeType: String,
        nombreArchivoAntiguo: String? = null
    ): String? {
        val requestBody = archivo.asRequestBody(mimeType.toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("file", nombreArchivo, requestBody)

        val oldFileRequestBody = nombreArchivoAntiguo?.let {
            it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        val response =
            RetrofitInstance.getFileUploadApi(context).uploadFile(multipart, oldFileRequestBody)

        return if (response.isSuccessful) response.body()?.string() else null

    }
}
package com.torre.b2c2c_tfg.ui.components


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import android.provider.OpenableColumns
import com.torre.b2c2c_tfg.ui.util.FileUtils.copyUriToTempFile
import com.torre.b2c2c_tfg.ui.util.FileUtils.getFileNameFromUri
import java.io.File

@Composable
fun UploadFileComponent(
    label: String,
    mimeType: String,
    storageKey: String,
    initialUri: Uri? = null,
    mostrarVistaPreviaImagen: Boolean = false,
    modifier: Modifier = Modifier,
    onFileReadyToUpload: (uri: Uri?, file: File?, nombre: String?) -> Unit
) {
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf(initialUri) }
    var fileName by remember { mutableStateOf<String?>(null) }

    // Cargar URI guardada si existe
    LaunchedEffect(Unit) {
        if (selectedUri == null) {
            val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUriString = prefs.getString(storageKey, null)
            val savedUri = savedUriString?.let { Uri.parse(it) }

            savedUri?.let { uri ->
                val hasPermission = context.contentResolver.persistedUriPermissions.any {
                    it.uri == uri && it.isReadPermission
                }

                if (hasPermission) {
                    selectedUri = uri
                    fileName = getFileNameFromUri(context, uri)
                    val tempFile = copyUriToTempFile(context, uri)
                    onFileReadyToUpload(uri, tempFile, fileName)
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString(storageKey, it.toString()).apply()

            selectedUri = it
            fileName = getFileNameFromUri(context, it)
            val tempFile = copyUriToTempFile(context, it)

            onFileReadyToUpload(it, tempFile, fileName)
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(onClick = { launcher.launch(mimeType) }) {
            Icon(Icons.Default.UploadFile, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label)
        }

        fileName?.let {
            Text("Archivo seleccionado: $it", style = MaterialTheme.typography.bodyMedium)
        }

        if (mostrarVistaPreviaImagen && selectedUri != null) {
            AsyncImage(
                model = selectedUri,
                contentDescription = "Vista previa",
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun UserSelectedImage(
    imageUri: Uri?,
    modifier: Modifier = Modifier
) {
    if (imageUri != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(true)
                .build(),
            contentDescription = "Imagen seleccionada por el usuario",
            modifier = modifier.size(150.dp)
        )
    }
}



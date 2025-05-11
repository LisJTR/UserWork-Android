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

@Composable
fun UploadFileImageComponent(
    onFileSelected: (Uri) -> Unit,
    mimeType: String = "*/*",
    initialUri: Uri? = null,
    modifier: Modifier = Modifier
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(initialUri) }
    var fileName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // ✅ Sincroniza selectedFileUri cuando cambia initialUri desde afuera
    LaunchedEffect(Unit) {
        if (selectedFileUri == null) {
            val prefs = context.getSharedPreferences("my_prefs", android.content.Context.MODE_PRIVATE)
            val uriString = prefs.getString("saved_file_uri", null)
            val uri = uriString?.let { Uri.parse(it) }

            if (uri != null) {
                val hasPermission = context.contentResolver.persistedUriPermissions.any {
                    it.uri == uri && it.isReadPermission
                }

                if (hasPermission) {
                    selectedFileUri = uri
                    onFileSelected(uri)

                    // Obtener nombre del archivo
                    val cursor = context.contentResolver.query(uri, null, null, null, null)
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (nameIndex != -1) {
                                fileName = it.getString(nameIndex)
                            }
                        }
                    }
                }
            }
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            onFileSelected(it)

            // 👉 TOMA PERMISOS PERSISTENTES
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            // 👉 GUARDA LA URI EN SharedPreferences
            val prefs = context.getSharedPreferences("my_prefs", android.content.Context.MODE_PRIVATE)
            prefs.edit().putString("saved_file_uri", it.toString()).apply()




            val cursor = context.contentResolver.query(it, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        fileName = it.getString(nameIndex)
                    }
                }
            }
        }
    }

    OutlinedButton(
        onClick = { filePickerLauncher.launch(mimeType) },
        modifier = modifier,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.UploadFile, contentDescription = null)
            if (selectedFileUri == null) {
                Text("Arrastra el archivo", style = MaterialTheme.typography.bodyMedium)
                Text("Seleccione archivo", style = MaterialTheme.typography.bodySmall)
            } else {
                Text(
                    text = fileName ?: "Archivo seleccionado",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    }
}

@Composable
fun UploadDocComponent(
    label: String = "Seleccionar archivo",
    mimeType: String = "application/*",
    initialUri: Uri? = null,
    storageKey: String,
    modifier: Modifier = Modifier,
    onFileSelected: (uri: Uri, fileName: String) -> Unit
) {
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(initialUri) }
    var fileName by remember { mutableStateOf<String?>(null) }

    // Recuperar desde SharedPreferences al entrar
    LaunchedEffect(Unit) {
        if (selectedUri == null) {
            val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUri = prefs.getString(storageKey, null)?.let { Uri.parse(it) }


            savedUri?.let { uri ->
                val hasPermission = context.contentResolver.persistedUriPermissions.any {
                    it.uri == uri && it.isReadPermission
                }
                if (hasPermission) {
                    selectedUri = uri
                    fileName = getFileNameFromUri(context, uri)
                    onFileSelected(uri, fileName!!)
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // 👉 Aquí sí: try-catch alrededor del permiso
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
            fileName?.let { name -> onFileSelected(it, name) }
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(onClick = { launcher.launch(mimeType) }) {
            Icon(Icons.Default.UploadFile, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label)
        }

        fileName?.let {
            Text("Archivo seleccionado: $it", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

// Helper para sacar el nombre del archivo
fun getFileNameFromUri(context: Context, uri: Uri): String? {
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex != -1) {
            return cursor.getString(nameIndex)
        }
    }

    // Fallback: usar parte del path del URI
    return uri.lastPathSegment?.substringAfterLast("/")
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



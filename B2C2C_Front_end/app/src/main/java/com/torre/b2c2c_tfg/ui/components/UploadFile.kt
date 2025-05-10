package com.torre.b2c2c_tfg.ui.components


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
fun UploadFileComponent(
    onFileSelected: (Uri) -> Unit,
    mimeType: String = "*/*",
    modifier: Modifier = Modifier
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            onFileSelected(it)

            // Aquí obtenemos el nombre real del archivo
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

    if (selectedFileUri == null) {
        OutlinedButton(
            onClick = { filePickerLauncher.launch(mimeType) },
            modifier = modifier,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
                Text("Arrastra el archivo", style = MaterialTheme.typography.bodyMedium)
                Text("Seleccione archivo", style = MaterialTheme.typography.bodySmall)
            }
        }
    } else {
        if (mimeType.startsWith("image/")) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(selectedFileUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen seleccionada",
                modifier = modifier
            )
        } else {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
                Text(
                    text = "Archivo seleccionado: ${fileName ?: "Nombre no disponible"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
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



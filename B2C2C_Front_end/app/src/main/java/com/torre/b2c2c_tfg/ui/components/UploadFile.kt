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

@Composable
fun UploadFileComponent(
    onFileSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            onFileSelected(it)
        }
    }

    //Se tiene que especificar Style para el OutlinedButton No toma el color por defecto
    OutlinedButton(
        onClick = { filePickerLauncher.launch("*/*") }, // Se podría limitar el tipo de archivo seleccionable: "application/pdf"
        modifier = modifier,
        border = BorderStroke(2.dp,  MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.UploadFile, contentDescription = null)
            Text(text = "Arrastra el archivo",
                style =
                    MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
            )
            Text(text = "Seleccione archivo",
                style =
                    MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    selectedFileUri?.let {
        Text(
            text = "Archivo seleccionado: ${it.lastPathSegment}",
            modifier = Modifier.padding(top = 8.dp)
        )
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



package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileCard(
    imageUrl: String,           // Imagen del alumno o empresa
    name: String,              //Nombre empresa o alumno
    sector: String,            //Sector / Título
    description: String,       //descripción
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberAsyncImagePainter(imageUrl)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = sector, style = MaterialTheme.typography.bodySmall)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

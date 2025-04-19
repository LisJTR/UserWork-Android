package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*




@Composable
fun OfferCardForm(
    title: String,
    description: String,
    aptitudes: String,
    queSeOfrece: String,
    isPublic: Boolean, // variable para controlar la visibilidad
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAptitudesChange: (String) -> Unit,
    onQueSeOfreceChange: (String) -> Unit,
    onDelete: () -> Unit,
    onView: () -> Unit,
    onHide: () -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = if (isPublic) 1f else 0.4f)
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            OutlinedInputTextField(
                value = title,
                onValueChange = onTitleChange,
                label = "Título oferta",
                enabled = isPublic
            )
            OutlinedInputTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = "Descripción de la oferta",
                enabled = isPublic
            )
            OutlinedInputTextField(
                value = aptitudes,
                onValueChange = onAptitudesChange,
                label = "Aptitudes",
                enabled = isPublic
            )
            OutlinedInputTextField(
                value = queSeOfrece,
                onValueChange = onQueSeOfreceChange,
                label = "¿Qué se ofrece?",
                enabled = isPublic
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
                IconButton(onClick = onView) {
                    Icon(Icons.Default.Visibility, contentDescription = "Marcar como visible")
                }
                IconButton(onClick = onHide) {
                    Icon(Icons.Default.VisibilityOff, contentDescription = "Marcar como oculta")
                }
            }
        }
    }
}

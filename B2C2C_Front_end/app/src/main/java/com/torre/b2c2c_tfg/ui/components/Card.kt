package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.Alignment


@Composable
fun OfferCardForm(
    id: Int? = null,
    title: String,
    description: String,
    aptitudes: String,
    queSeOfrece: String,
    isPublic: Boolean, // variable para controlar la visibilidad
    isSaved: Boolean, // variable para controlar si se ha guardado
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAptitudesChange: (String) -> Unit,
    onQueSeOfreceChange: (String) -> Unit,
    onDelete: () -> Unit,
    onView: () -> Unit,
    onHide: () -> Unit
) {
    val backgroundColor = when {
        !isSaved -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f) // NUEVA (más oscura)
        isPublic -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 1f)   // GUARDADA y pública
        else -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.8f)     // GUARDADA pero privada
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(Modifier.padding(16.dp)) {
            id?.let {
                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            OutlinedInputTextField(
                value = title,
                onValueChange = onTitleChange,
                label = "Título oferta",
            )
            OutlinedInputTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = "Descripción de la oferta",

            )
            OutlinedInputTextField(
                value = aptitudes,
                onValueChange = onAptitudesChange,
                label = "Aptitudes",

            )
            OutlinedInputTextField(
                value = queSeOfrece,
                onValueChange = onQueSeOfreceChange,
                label = "¿Qué se ofrece?",

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

@Composable
fun HabilidadChip(
    habilidad: String,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = habilidad,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onRemove, modifier = Modifier.size(16.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Eliminar", modifier = Modifier.size(16.dp))
        }
    }
}


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OfferCard(
    title: String,
    description: String,
    onDelete: () -> Unit,
    onView: () -> Unit,
    onHide: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("• $title", style = MaterialTheme.typography.titleSmall)
            Text("• $description", style = MaterialTheme.typography.bodyMedium)
            Text("   • Requisitos mínimos", style = MaterialTheme.typography.bodySmall)
            Text("   • Habilidades necesarias", style = MaterialTheme.typography.bodySmall)

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
                IconButton(onClick = onView) {
                    Icon(Icons.Default.Visibility, contentDescription = "Ver")
                }
                IconButton(onClick = onHide) {
                    Icon(Icons.Default.VisibilityOff, contentDescription = "Ocultar")
                }
            }
        }
    }
}

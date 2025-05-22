package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.torre.b2c2c_tfg.ui.theme.Orange

@Composable
fun PerfilProgresoCompleto(porcentaje: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Perfil completado: ${(porcentaje * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
        LinearProgressIndicator(
            progress = porcentaje.coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = Orange
        )
    }
}
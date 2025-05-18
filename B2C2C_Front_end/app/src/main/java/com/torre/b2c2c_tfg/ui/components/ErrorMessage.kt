package com.torre.b2c2c_tfg.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    if (message.isNotBlank()) {
        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )
    }
}

@Composable
fun ErrorMessageGeneral(message: String, modifier: Modifier = Modifier) {
    if (message.isNotBlank()) {
        Text(
            text = "",
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )
    }
}
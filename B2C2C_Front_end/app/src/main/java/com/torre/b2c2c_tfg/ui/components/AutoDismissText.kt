package com.torre.b2c2c_tfg.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun AutoDismissErrorText(
    text: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2000
) {

    AnimatedVisibility(visible = !text.isNullOrBlank()) {
    if (!text.isNullOrBlank()) {
        LaunchedEffect(text) {
            delay(durationMillis)
            onDismiss() // Oculta el mensaje
        }

        Text(
            text = text,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )
    }
    }
}

@Composable
fun AutoDismissCorrectText(
    text: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2000
) {
        AnimatedVisibility(visible = !text.isNullOrBlank()) {
        if (!text.isNullOrBlank()) {
            LaunchedEffect(text) {
                delay(durationMillis)
                onDismiss() // Oculta el mensaje
            }

            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = modifier
            )
        }
    }
}
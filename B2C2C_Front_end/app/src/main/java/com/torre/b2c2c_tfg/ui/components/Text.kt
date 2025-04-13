package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//Campo de texto para el titulo
@Composable
fun ScreenTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

//Campo de texto para la descripcion
@Composable
fun SectionDescription(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

//Campo de texto para el titulo
@Composable
fun TextTitle(text: String){

    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

//Campo de texto para el Field
@Composable
fun TextInputLabel (text: String, modifier: Modifier = Modifier){

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

//Campo de texto para introducir con bordes
@Composable
fun InputTextField ( value: String,
                     onValueChange: (String) -> Unit,
                     label: String,
                     modifier: Modifier = Modifier
){
    androidx.compose.material3.TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
    )
}

//Campo de texto para introducir con subrayado
@Composable
fun OutlinedInputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
    )
}


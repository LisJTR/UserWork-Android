package com.torre.b2c2c_tfg.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

//Campo de texto para el titulo
@Composable
fun ScreenTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
    )
}

//Campo de texto para la descripcion
@Composable
fun SectionDescription(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
    )
}

//Campo de texto para el titulo
@Composable
fun TextTitle(
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    color: Color? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        color = color ?: MaterialTheme.colorScheme.onSurface,
        modifier = modifier
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
    modifier : Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,


            )
        },
        singleLine = true,
        modifier = modifier,
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodySmall,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon, 
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary

        )
    )
}





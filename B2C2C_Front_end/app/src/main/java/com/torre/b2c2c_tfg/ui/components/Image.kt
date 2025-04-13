package com.torre.b2c2c_tfg.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.torre.b2c2c_tfg.R

@Composable
fun Applogo(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = R.drawable.logouserwork),
        contentDescription = "Logo de la app",
        modifier = modifier
            )

}


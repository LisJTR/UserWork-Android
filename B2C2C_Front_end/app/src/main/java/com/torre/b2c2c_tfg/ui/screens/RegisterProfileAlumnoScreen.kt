package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.ui.components.BottomBar

@Composable
fun RegisterProfileAlumnoScreen(navController: NavController , contentPadding: PaddingValues = PaddingValues()) {
    //Logica
    Text(text = "Pantalla Login Empresa")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun LoginAlumnoScreenPreview() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, isUserEmpresa = false)
        }
    ) {
        RegisterProfileAlumnoScreen(navController = navController)
    }
}




package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.util.UserType


@Composable
fun OfertaScreen(navController: NavController, isUserEmpresa: Boolean) {
    //Logica
    Text(text = "Pantalla Home")



}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun OfertaScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, userType = UserType.ALUMNO)
        }
    ) {
        OfertaScreen(navController = navController, isUserEmpresa = true)
    }
}


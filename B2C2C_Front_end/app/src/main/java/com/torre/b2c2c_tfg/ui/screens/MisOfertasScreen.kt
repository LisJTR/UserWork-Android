package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel

@Composable
fun MisOfertasScreen(navController: NavController, isUserEmpresa: Boolean) {
    if (isUserEmpresa) {
        Text("Mis Ofertas - Empresa")
        // Mostrar ofertas de empresa
    } else {
        Text("Mis Ofertas - Alumno")
        // Mostrar ofertas del alumno
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun MisOfertasScreen() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            MisOfertasScreen( navController = navController, isUserEmpresa = true)
        }
    }
}


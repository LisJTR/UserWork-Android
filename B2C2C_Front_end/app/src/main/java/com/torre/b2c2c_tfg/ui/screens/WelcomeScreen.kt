package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.R
import com.torre.b2c2c_tfg.ui.components.Applogo
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.ScreenTitle
import com.torre.b2c2c_tfg.ui.components.SectionDescription
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme


@Composable
fun WelcomeScreen(navController: NavController) {

    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {

        Applogo(
            modifier = Modifier.size(250.dp)
        )
        SectionDescription(text = "Explora ofertas de prácticas, conecta con empresas y da el primer paso en tu carrera profesional.")
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                40.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonGeneric(
                text = "Alumno",
                onClick = { navController.navigate("loginAlumnno") }
            )
            ButtonGeneric(
                text = "Empresa",
                onClick = { navController.navigate("loginEmpresa") }
            )

        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview()
@Composable
fun WelcomeScreenPreview() {
    // Crea un navController "falso" solo para previsualizar
    Scaffold {
        B2C2C_TFGTheme() {

            WelcomeScreen(navController = rememberNavController())
        }
    }
}




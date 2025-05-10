package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.IconMessage
import com.torre.b2c2c_tfg.ui.components.ProfileCard
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel


@Composable
fun OfertasScreen(navController: NavController, sessionViewModel: SessionViewModel, isUserEmpresa: Boolean) {
    val context = LocalContext.current
    val viewModel = remember {
        OfertasViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(
                AlumnoRepositoryImpl(
                    RetrofitInstance.getInstance(
                        context
                    )
                )
            ),
            getEmpresaUseCase = GetEmpresaUseCase(
                EmpresaRepositoryImpl(
                    RetrofitInstance.getInstance(
                        context
                    )
                )
            )
        )
    }

    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value

    val alumno by viewModel.alumno.collectAsState()
    val empresa by viewModel.empresa.collectAsState()

    LaunchedEffect(userId, userType) {
        if (userType == "alumno") viewModel.cargarAlumno(userId)
        else if (userType == "empresa") viewModel.cargarEmpresa(userId)
    }


    Column {
        Spacer(modifier = Modifier.height(30.dp)) // espacio superior

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconMessage(
                onClick = { /* TODO */ }
            )
        }

        if (userType == "alumno" && alumno != null) {
            ProfileCard(
                imageUrl = alumno!!.imagen ?: "",
                name = "${alumno!!.nombre} ${alumno!!.apellido}"
            )
        }

        if (userType == "empresa" && empresa != null) {
            ProfileCard(
                imageUrl = empresa!!.imagen ?: "",
                name = empresa!!.nombre ?: ""
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun OfertasScreen() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(

            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            OfertasScreen(navController = navController, sessionViewModel = SessionViewModel() , isUserEmpresa = true)
        }
    }
}

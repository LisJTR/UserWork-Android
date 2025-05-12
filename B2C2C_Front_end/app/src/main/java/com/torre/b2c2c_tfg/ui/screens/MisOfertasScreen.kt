package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.HeaderContentofScreens
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel


@Composable
fun MisOfertasScreen(navController: NavController, isUserEmpresa: Boolean, sessionViewModel: SessionViewModel) {

    val context = LocalContext.current
    val viewModel = remember {
        OfertasScreenViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            getEmpresaUseCase = GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getSectoresUnicosUseCase = GetSectoresUnicosUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getTitulacionesUnicasUseCase = GetTitulacionesUnicasUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            alumnoRepository = AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)),
            empresaRepository = EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))

        )
    }

    HeaderContentofScreens(
        sessionViewModel = sessionViewModel,
        viewModel = viewModel,
        onFiltroSeleccionado = { seleccion ->
            println("Filtro seleccionado en pantalla: $seleccion")
        }
    )
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
            MisOfertasScreen( navController = navController, isUserEmpresa = true, sessionViewModel = SessionViewModel())
        }
    }
}

